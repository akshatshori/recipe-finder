package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class NA(
    @SerializedName("label")
    val label: String,
    @SerializedName("quantity")
    val quantity: Double,
    @SerializedName("unit")
    val unit: String
)