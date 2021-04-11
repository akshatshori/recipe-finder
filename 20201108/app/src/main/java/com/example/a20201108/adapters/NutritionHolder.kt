package com.example.a20201108.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a20201108.R
import com.ramotion.foldingcell.FoldingCell

class NutritionHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.nutrition_item, parent, false)) {
    private var iTititle: TextView? = null
    private var iKey1: TextView? = null
    private var iValue1: TextView? = null
    private var iKey2: TextView? = null
    private var iValue2: TextView? = null
    private var iKey3: TextView? = null
    private var iValue3: TextView? = null
    private var iKey4: TextView? = null
    private var iValue4: TextView? = null

    private var foldcell : FoldingCell? = null


    init {
        iTititle = itemView.findViewById(R.id.nutrition_title_txtview)
        iKey1 = itemView.findViewById(R.id.nutrition_key1_txtview)
        iValue1 = itemView.findViewById(R.id.nutrition_value1_txtview)
        iKey2 = itemView.findViewById(R.id.nutrition_key2_txtview)
        iValue2 = itemView.findViewById(R.id.nutrition_value2_txtview)
        iKey3 = itemView.findViewById(R.id.nutrition_key3_txtview)
        iValue3 = itemView.findViewById(R.id.nutrition_value3_txtview)
        iKey4 = itemView.findViewById(R.id.nutrition_key4_txtview)
        iValue4 = itemView.findViewById(R.id.nutrition_value4_txtview)

        foldcell = itemView.findViewById(R.id.nutrition_folding_cell)
    }

    fun bind(nut: Nutrition) {
        iTititle?.text = nut.title
        iKey1?.text = nut.value1
        iValue1?.text = nut.Key1
        iKey2?.text = nut.value2
        iValue2?.text = nut.Key2
        iKey3?.text = nut.value3
        iValue3?.text = nut.Key3
        iKey4?.text = nut.value4
        iValue4?.text = nut.Key4

        foldcell?.setOnClickListener {

            foldcell?.toggle(false)
        }

    }

}