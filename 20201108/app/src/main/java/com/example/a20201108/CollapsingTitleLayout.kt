/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.a20201108

import android.content.Context

import android.graphics.*
import android.os.Build

import androidx.core.view.ViewCompat;
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.Toolbar


class CollapsingTitleLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        // Pre-JB-MR2 doesn't support HW accelerated canvas scaled text so we will workaround it
        // by using our own texture
        private val USE_SCALING_TEXTURE = Build.VERSION.SDK_INT < 18
        private const val DEBUG_DRAW = false
        private var DEBUG_DRAW_PAINT: Paint? = null

        /**
         * Recursive binary search to find the best size for the text
         *
         * Adapted from https://github.com/grantland/android-autofittextview
         */
        private fun getSingleLineTextSize(text: String?, paint: TextPaint, targetWidth: Float,
                                          low: Float, high: Float, precision: Float, metrics: DisplayMetrics): Float {
            val mid = (low + high) / 2.0f
            paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics)
            val maxLineWidth = paint.measureText(text)
            return if (high - low < precision) {
                low
            } else if (maxLineWidth > targetWidth) {
                getSingleLineTextSize(text, paint, targetWidth, low, mid, precision, metrics)
            } else if (maxLineWidth < targetWidth) {
                getSingleLineTextSize(text, paint, targetWidth, mid, high, precision, metrics)
            } else {
                mid
            }
        }

        /**
         * Returns true if `value` is 'close' to it's closest decimal value. Close is currently
         * defined as it's difference being < 0.01.
         */
        private fun isClose(value: Float, targetValue: Float): Boolean {
            return Math.abs(value - targetValue) < 0.01f
        }

        /**
         * Interpolate between `startValue` and `endValue`, using `progress`.
         */
        private fun interpolate(startValue: Float, endValue: Float, progress: Float): Float {
            return startValue + (endValue - startValue) * progress
        }

        init {
            DEBUG_DRAW_PAINT = if (DEBUG_DRAW) Paint() else null
            if (DEBUG_DRAW_PAINT != null) {
                DEBUG_DRAW_PAINT!!.setAntiAlias(true)
                DEBUG_DRAW_PAINT!!.setColor(Color.MAGENTA)
            }
        }
    }

    private var mToolbar: Toolbar? = null
    private var mDummyView: View? = null
    private var mScrollOffset = 0f
    private val mToolbarContentBounds: Rect
    private var mExpandedMarginLeft: Float
    private var mExpandedMarginRight: Float
    private var mExpandedMarginBottom: Float
    private val mRequestedExpandedTitleTextSize: Int
    private var mExpandedTitleTextSize = 0
    private var mCollapsedTitleTextSize = 0
    private var mExpandedTop = 0f
    private var mCollapsedTop = 0f
    private var mTitle: String? = null
    private var mTitleToDraw: String? = null
    private var mUseTexture = false
    private var mExpandedTitleTexture: Bitmap? = null
    private var mTextLeft = 0f
    private var mTextRight = 0f
    private var mTextTop = 0f
    private var mScale = 0f
    private val mTextPaint: TextPaint
    private var mTexturePaint: Paint? = null
    private val mTextSizeInterpolator: Interpolator?
    fun setTextAppearance(resId: Int) {
        val atp = context.obtainStyledAttributes(resId,
                R.styleable.CollapsingTextAppearance)
        mTextPaint.color = atp.getColor(
                R.styleable.CollapsingTextAppearance_android_textColor, Color.WHITE)
        mCollapsedTitleTextSize = atp.getDimensionPixelSize(
                R.styleable.CollapsingTextAppearance_android_textSize, 0)
        atp.recycle()
        recalculate()
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        if (child is Toolbar) {
            mToolbar = child
            mDummyView = View(context)
            mToolbar!!.addView(mDummyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    /**
     * Set the value indicating the current scroll value. This decides how much of the
     * background will be displayed, as well as the title metrics/positioning.
     *
     * A value of `0.0` indicates that the layout is fully expanded.
     * A value of `1.0` indicates that the layout is fully collapsed.
     */
    fun setScrollOffset(offset: Float) {
        if (offset != mScrollOffset) {
            mScrollOffset = offset
            calculateOffsets()
        }
    }

    private fun calculateOffsets() {
        val offset = mScrollOffset
        val textSizeOffset = mTextSizeInterpolator?.getInterpolation(mScrollOffset) ?: offset
        mTextLeft = interpolate(mExpandedMarginLeft, mToolbarContentBounds.left.toFloat(), offset)
        mTextTop = interpolate(mExpandedTop, mCollapsedTop, offset)
        mTextRight = interpolate(width - mExpandedMarginRight, mToolbarContentBounds.right.toFloat(), offset)
        setInterpolatedTextSize(
                interpolate(mExpandedTitleTextSize.toFloat(), mCollapsedTitleTextSize.toFloat(), textSizeOffset))
        ViewCompat.postInvalidateOnAnimation(this)
    }

    private fun calculateTextBounds() {
        val metrics = resources.displayMetrics

        // We then calculate the collapsed text size, using the same logic
        mTextPaint.textSize = mCollapsedTitleTextSize.toFloat()
        val textHeight = mTextPaint.descent() - mTextPaint.ascent()
        val textOffset = textHeight / 2 - mTextPaint.descent()
        mCollapsedTop = mToolbarContentBounds.centerY() + textOffset

        // First, let's calculate the expanded text size so that it fit within the bounds
        // We make sure this value is at least our minimum text size
        mExpandedTitleTextSize = Math.max(mCollapsedTitleTextSize.toFloat(),
                getSingleLineTextSize(mTitle, mTextPaint,
                        width - mExpandedMarginLeft - mExpandedMarginRight, 0f,
                        mRequestedExpandedTitleTextSize.toFloat(), 0.5f, metrics)).toInt()
        mExpandedTop = height - mExpandedMarginBottom

        // The bounds have changed so we need to clear the texture
        clearTexture()
    }

    override fun draw(canvas: Canvas) {
        val saveCount = canvas.save()
        val toolbarHeight = mToolbar!!.height
        canvas.clipRect(0f, 0f, canvas.width.toFloat(),
                interpolate(canvas.height.toFloat(), toolbarHeight.toFloat(), mScrollOffset))

        // Now call super and let it draw the background, etc
        super.draw(canvas)
        if (mTitleToDraw != null) {
            val x = mTextLeft
            var y = mTextTop
            val ascent = mTextPaint.ascent() * mScale
            val descent = mTextPaint.descent() * mScale
            val h = descent - ascent
            if (DEBUG_DRAW) {
                // Just a debug tool, which drawn a Magneta rect in the text bounds
                canvas.drawRect(mTextLeft,
                        y - h + descent,
                        mTextRight,
                        y + descent,
                        DEBUG_DRAW_PAINT!!)
            }
            if (mUseTexture) {
                y = y - h + descent
            }
            if (mScale != 1f) {
                canvas.scale(mScale, mScale, x, y)
            }
            if (mUseTexture && mExpandedTitleTexture != null) {
                // If we should use a texture, draw it instead of text
                canvas.drawBitmap(mExpandedTitleTexture!!, x, y, mTexturePaint)
            } else {
                canvas.drawText(mTitleToDraw!!, x, y, mTextPaint)
            }
        }
        canvas.restoreToCount(saveCount)
    }

    private fun setInterpolatedTextSize(textSize: Float) {
        if (mTitle == null) return
        if (isClose(textSize, mCollapsedTitleTextSize.toFloat()) || isClose(textSize, mExpandedTitleTextSize.toFloat())
                || mTitleToDraw == null) {
            // If the text size is 'close' to being a decimal, then we use this as a sync-point.
            // We disable our manual scaling and set the paint's text size.
            mTextPaint.textSize = textSize
            mScale = 1f

            // We also use this as an opportunity to ellipsize the string
            val title = TextUtils.ellipsize(mTitle, mTextPaint,
                    mTextRight - mTextLeft,
                    TextUtils.TruncateAt.END)
            if (title !== mTitleToDraw) {
                // If the title has changed, turn it into a string
                mTitleToDraw = title.toString()
            }
            if (USE_SCALING_TEXTURE && isClose(textSize, mExpandedTitleTextSize.toFloat())) {
                ensureExpandedTexture()
            }
            mUseTexture = false
        } else {
            // We're not close to a decimal so use our canvas scaling method
            mScale = if (mExpandedTitleTexture != null) {
                textSize / mExpandedTitleTextSize
            } else {
                textSize / mTextPaint.textSize
            }
            mUseTexture = USE_SCALING_TEXTURE
        }
        ViewCompat.postInvalidateOnAnimation(this)
    }

    private fun ensureExpandedTexture() {
        if (mExpandedTitleTexture != null) return
        val w = (width - mExpandedMarginLeft - mExpandedMarginRight).toInt()
        val h = (mTextPaint.descent() - mTextPaint.ascent()).toInt()
        mExpandedTitleTexture = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(mExpandedTitleTexture!!)
        c.drawText(mTitleToDraw!!, 0f, h - mTextPaint.descent(), mTextPaint)
        if (mTexturePaint == null) {
            // Make sure we have a paint
            mTexturePaint = Paint()
            mTexturePaint!!.isAntiAlias = true
            mTexturePaint!!.isFilterBitmap = true
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mToolbarContentBounds.left = mDummyView!!.left
        mToolbarContentBounds.top = mDummyView!!.top
        mToolbarContentBounds.right = mDummyView!!.right
        mToolbarContentBounds.bottom = mDummyView!!.bottom
        if (changed && mTitle != null) {
            // If we've changed and we have a title, re-calculate everything!
            recalculate()
        }
    }

    private fun recalculate() {
        if (height > 0) {
            calculateTextBounds()
            calculateOffsets()
        }
    }

    /**
     * Set the title to display
     *
     * @param title
     */
    fun setTitle(title: String?) {
        if (title == null || title != mTitle) {
            mTitle = title
            clearTexture()
            if (height > 0) {
                // If we've already been laid out, calculate everything now otherwise we'll wait
                // until a layout
                recalculate()
            }
        }
    }

    private fun clearTexture() {
        if (mExpandedTitleTexture != null) {
            mExpandedTitleTexture!!.recycle()
            mExpandedTitleTexture = null
        }
    }

    init {
        mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        val a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingTitleLayout)
        mExpandedMarginBottom = a.getDimensionPixelSize(R.styleable.CollapsingTitleLayout_expandedMargin, 0).toFloat()
        mExpandedMarginRight = mExpandedMarginBottom
        mExpandedMarginLeft = mExpandedMarginRight
        val isRtl = (ViewCompat.getLayoutDirection(this)
                === ViewCompat.LAYOUT_DIRECTION_RTL)
        if (a.hasValue(R.styleable.CollapsingTitleLayout_expandedMarginStart)) {
            val marginStart = a.getDimensionPixelSize(
                    R.styleable.CollapsingTitleLayout_expandedMarginStart, 0)
            if (isRtl) {
                mExpandedMarginRight = marginStart.toFloat()
            } else {
                mExpandedMarginLeft = marginStart.toFloat()
            }
        }
        if (a.hasValue(R.styleable.CollapsingTitleLayout_expandedMarginEnd)) {
            val marginEnd = a.getDimensionPixelSize(
                    R.styleable.CollapsingTitleLayout_expandedMarginEnd, 0)
            if (isRtl) {
                mExpandedMarginLeft = marginEnd.toFloat()
            } else {
                mExpandedMarginRight = marginEnd.toFloat()
            }
        }
        if (a.hasValue(R.styleable.CollapsingTitleLayout_expandedMarginBottom)) {
            mExpandedMarginBottom = a.getDimensionPixelSize(
                    R.styleable.CollapsingTitleLayout_expandedMarginBottom, 0).toFloat()
        }
        val tp = a.getResourceId(R.styleable.CollapsingTitleLayout_android_textAppearance,
                android.R.style.TextAppearance)
        setTextAppearance(tp)
        if (a.hasValue(R.styleable.CollapsingTitleLayout_collapsedTextSize)) {
            mCollapsedTitleTextSize = a.getDimensionPixelSize(
                    R.styleable.CollapsingTitleLayout_collapsedTextSize, 0)
        }
        mRequestedExpandedTitleTextSize = a.getDimensionPixelSize(
                R.styleable.CollapsingTitleLayout_expandedTextSize, mCollapsedTitleTextSize)
        val interpolatorId = a
                .getResourceId(R.styleable.CollapsingTitleLayout_textSizeInterpolator,
                        android.R.anim.accelerate_interpolator)
        mTextSizeInterpolator = AnimationUtils.loadInterpolator(context, interpolatorId)
        a.recycle()
        mToolbarContentBounds = Rect()
        setWillNotDraw(false)
    }
}