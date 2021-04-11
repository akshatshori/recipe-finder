package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("calories")
    val calories: Double,
    @SerializedName("cautions")
    val cautions: List<String>,
    @SerializedName("dietLabels")
    val dietLabels: List<String>,
    @SerializedName("digest")
    val digest: List<Digest>,
    @SerializedName("healthLabels")
    val healthLabels: List<String>,
    @SerializedName("image")
    val image: String,
    @SerializedName("ingredientLines")
    val ingredientLines: List<String>,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("label")
    val label: String,
    @SerializedName("shareAs")
    val shareAs: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("totalDaily")
    val totalDaily: TotalDaily,
    @SerializedName("totalNutrients")
    val totalNutrients: TotalNutrients,
    @SerializedName("totalTime")
    val totalTime: Double,
    @SerializedName("totalWeight")
    val totalWeight: Double,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("yield")
    val yield: Double
)