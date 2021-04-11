package com.example.a20201108.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a20201108.R
import kotlinx.android.synthetic.main.nutrition_item.view.*


class NutritionAdapter(private val list: List<Nutrition>)
    : RecyclerView.Adapter<NutritionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NutritionHolder(inflater, parent)

    }

    override fun onBindViewHolder(holder: NutritionHolder, position: Int) {
        val movie: Nutrition = list[position]
    if (position == 0) {
     holder.itemView.nutrition_cell_title_view.setBackgroundColor(Color.rgb(208, 102, 250))
        holder.itemView.nutrition_cell_content_view.setBackgroundColor(Color.rgb(208, 102, 250))
        holder.itemView.nutrition_image.setBackgroundResource(R.drawable.ic_recipe);
      //  holder.itemView.nutrition_value4_txtview.width =0
     }
        if (position == 1) {
            holder.itemView.nutrition_cell_title_view.setBackgroundColor(Color.rgb(255, 95, 95))
            holder.itemView.nutrition_cell_content_view.setBackgroundColor(Color.rgb(255, 95, 95))
            holder.itemView.nutrition_image.setBackgroundResource(R.drawable.ic_bread);
        }
        if (position == 2) {
            holder.itemView.nutrition_cell_title_view.setBackgroundColor(Color.rgb(150, 210, 0))
            holder.itemView.nutrition_cell_content_view.setBackgroundColor(Color.rgb(150, 210, 0))
            holder.itemView.nutrition_image.setBackgroundResource(R.drawable.ic_fat);
        }
        if (position ==3)
        {
            holder.itemView.nutrition_cell_title_view.setBackgroundColor(Color.rgb(0, 168, 255))
            holder.itemView.nutrition_cell_content_view.setBackgroundColor(Color.rgb(0, 168, 255))
            holder.itemView.nutrition_image.setBackgroundResource(R.drawable.ic_lime);
        }
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}