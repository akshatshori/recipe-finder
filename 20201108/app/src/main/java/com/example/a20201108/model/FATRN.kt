package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class FATRN(
    @SerializedName("label")
    val label: String,
    @SerializedName("quantity")
    val quantity: Double,
    @SerializedName("unit")
    val unit: String
)