package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class Hit(
    @SerializedName("bookmarked")
    val bookmarked: Boolean,
    @SerializedName("bought")
    val bought: Boolean,
    @SerializedName("recipe")
    val recipe: Recipe
)