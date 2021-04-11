package com.example.a20201108

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a20201108.adapters.Nutrition
import com.example.a20201108.model.Hit
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*


class MyAdapter (private val dataList: MutableList<Hit>) : RecyclerView.Adapter<Holder>()  {
    private lateinit var context: Context





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        return Holder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun getItemCount(): Int = dataList.size



    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = dataList[position]

        val recipeLabelTextView = holder.itemView.recipe_list_title
        val recipeImagerView = holder.itemView.recipe_avatar
        val recipesubtitleTextView = holder.itemView.recipe_list_subtitle

        val label = "${data.recipe.label}"
        var subtitle = "${data.recipe.healthLabels}"
        recipeLabelTextView.text = label
       subtitle = subtitle.replace("[","")
       subtitle =  subtitle.replace("]","")
        Log.d(TAG, subtitle)
        recipesubtitleTextView.text = subtitle
        Picasso.get()
                .load(data.recipe.image)
                .into(recipeImagerView)


        val titleTypeFace = ResourcesCompat.getFont(context, R.font.josefinsans_bold)
        recipeLabelTextView.typeface = titleTypeFace

        val subtitleTypeFace = ResourcesCompat.getFont(context, R.font.josefinsans_semibolditalic)
        recipesubtitleTextView.typeface = subtitleTypeFace


        holder.itemView.setOnClickListener{
            //  Toast.makeText(context, "${data.recipe.ingredients}",Toast.LENGTH_SHORT).show()
            Log.d(TAG, "${data.recipe.ingredientLines}")
            val dialogBuilder = AlertDialog.Builder(context)


    //send data
            val nutList = ArrayList<Nutrition>()
            nutList.add(Nutrition("Recipe",
                    "Calories","${data.recipe.calories.toInt()}",
                    "Servings","${data.recipe.yield.toInt()}",
                    "Ingredients","${data.recipe.ingredientLines.count()}",
                    "Weight","${data.recipe.totalWeight.toInt()}"+"g"))

            nutList.add(Nutrition("Carbs & Protein",
                    "Fiber","${data.recipe.totalNutrients.fIBTG.quantity.toInt()}"+"${data.recipe.totalNutrients.fIBTG.unit}",
                    "Sugar","${data.recipe.totalNutrients.sUGAR.quantity.toInt()}"+"${data.recipe.totalNutrients.sUGAR.unit}",
                    "Protein","${data.recipe.totalNutrients.pROCNT.quantity.toInt()}"+"${data.recipe.totalNutrients.pROCNT.unit}",
                    "Cholestrol","${data.recipe.totalNutrients.cHOLE.quantity.toInt()}"+"${data.recipe.totalNutrients.pROCNT.unit}"))

            nutList.add(Nutrition("Fat",
                    "Saturated","${data.recipe.totalNutrients.fASAT.quantity.toInt()}"+"${data.recipe.totalNutrients.fASAT.unit}",
                    "Trans","${data.recipe.totalNutrients.sUGAR.quantity.toInt()}"+"${data.recipe.totalNutrients.sUGAR.unit}",
                    "MonoUnsat","${data.recipe.totalNutrients.pROCNT.quantity.toInt()}"+"${data.recipe.totalNutrients.pROCNT.unit}",
                    "PolyUnsat","${data.recipe.totalNutrients.pROCNT.quantity.toInt()}"+"${data.recipe.totalNutrients.pROCNT.unit}"))

            nutList.add(Nutrition("Vitamins",
                    "Vitamin A","${data.recipe.totalNutrients.vITARAE.quantity.toInt()}"+"${data.recipe.totalNutrients.vITARAE.unit}",
                    "Vitamin C","${data.recipe.totalNutrients.vITC.quantity.toInt()}"+"${data.recipe.totalNutrients.vITC.unit}",
                    "Vitamin E","${data.recipe.totalNutrients.tOCPHA.quantity.toInt()}"+"${data.recipe.totalNutrients.tOCPHA.unit}",
                    "Vitamin D","${data.recipe.totalNutrients.vITD.quantity.toInt()}"+"${data.recipe.totalNutrients.vITD.unit}"))






            val json = Gson().toJson(nutList)

            val intent = Intent(context, MainActivity3::class.java)
    intent.putExtra("recipeImg","${data.recipe.image}")
            intent.putExtra("intstructions","${data.recipe.ingredients}")
            intent.putExtra("nutlist",json)
            intent.putExtra("label","${data.recipe.label}")
            intent.putExtra("website","${data.recipe.url}")

            //intent.putStringArrayListExtra("intstructions","${data.recipe.ingredientLines}")
           // putExtra("instructions","${data.recipe.ingredients}")
    context.startActivity(intent)


}





}







}