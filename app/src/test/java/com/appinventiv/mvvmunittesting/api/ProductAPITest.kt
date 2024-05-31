package com.appinventiv.mvvmunittesting.api

import com.appinventiv.mvvmunittesting.Util
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var productsAPI: ProductsAPI


    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        productsAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductsAPI::class.java)
    }
    @Test
    fun testGetProducts() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = productsAPI.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun testGetProducts_returnProducts() = runTest{
        val mockResponse = MockResponse()
        val fileData = Util.readResourceFile("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(fileData)
        mockWebServer.enqueue(mockResponse)

        val response = productsAPI.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(2, response.body()!!.size)
    }

    @Test
    fun testGetProducts_returnError() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = productsAPI.getProducts()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
    }
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}