package com.example.nammavastra.model

data class Trend(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val category: String = "Silk"
)
