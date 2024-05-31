package com.appinventiv.mvvmunittesting.repository

import com.appinventiv.mvvmunittesting.api.NetworkResult
import com.appinventiv.mvvmunittesting.api.ProductsAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ProductsAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductsAPI::class.java)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setResponseCode(404)
        )
        val sut = ProductRepository(apiService)
        val result = sut.getProducts()
        val request = mockWebServer.takeRequest()

        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

}