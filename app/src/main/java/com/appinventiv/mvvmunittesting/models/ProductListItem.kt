package com.appinventiv.mvvmunittesting.models

data class ProductListItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
)