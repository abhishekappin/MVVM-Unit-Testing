package com.appinventiv.mvvmunittesting.api

import com.appinventiv.mvvmunittesting.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {
    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItem>>

}