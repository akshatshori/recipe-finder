package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("image")
    val image: Any,
    @SerializedName("text")
    val text: String,
    @SerializedName("weight")
    val weight: Double
)