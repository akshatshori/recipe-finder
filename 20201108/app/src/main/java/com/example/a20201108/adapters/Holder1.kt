package com.example.a20201108.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a20201108.R
import com.ramotion.foldingcell.FoldingCell
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.instruction_listitem.view.*

//class Holder1(itemView: View) : RecyclerView.ViewHolder(itemView)
//{

//}

class Holder1(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.instruction_listitem, parent, false)) {
    private var iTititle: TextView? = null
    private var iImgview: ImageView? = null



    init {
        iTititle = itemView.findViewById(R.id.instruction_list_title)
        iImgview = itemView.findViewById(R.id.instruc_avatar)

    }

    fun bind(instruc: Instructions) {
        iTititle?.text = instruc.title

        Picasso.get()
                .load(instruc.imgString)
                .into(iImgview)

    }

}