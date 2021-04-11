package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class edamam(
    @SerializedName("count")
    val count: Int,
    @SerializedName("from")
    val from: Int,
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("more")
    val more: Boolean,
    @SerializedName("q")
    val q: String,
    @SerializedName("to")
    val to: Int
)