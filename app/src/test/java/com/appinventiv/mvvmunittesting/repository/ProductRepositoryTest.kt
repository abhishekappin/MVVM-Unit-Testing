package com.appinventiv.mvvmunittesting.repository

import android.util.proto.ProtoOutputStream
import com.appinventiv.mvvmunittesting.api.NetworkResult
import com.appinventiv.mvvmunittesting.api.ProductsAPI
import com.appinventiv.mvvmunittesting.models.ProductListItem
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class ProductRepositoryTest {

    @Mock
    lateinit var productsAPI: ProductsAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProduct_EmptyList() = runTest{
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(emptyList()))
        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetProduct_expectedProductList() = runTest{
        val productList = listOf<ProductListItem>(
            ProductListItem("","", 1, "", 30.0, "Product 1"),
            ProductListItem("","", 2, "", 40.2, "Product 2")
        )
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(productList))
        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals(2, result.data!!.size)
        Assert.assertEquals("Product 1", result.data!!.get(0).title)
        Assert.assertEquals(30.0, result.data!!.get(0).price, 0.1)
    }
    @Test
    fun testGetProduct_expectedError() = runTest{
        Mockito.`when`(productsAPI.getProducts())
            .thenReturn(Response.error(401, "Unauthorized".toResponseBody()))
        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("Something went wrong", result.message)
    }
}