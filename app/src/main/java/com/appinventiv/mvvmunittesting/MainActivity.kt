package com.appinventiv.mvvmunittesting

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.appinventiv.mvvmunittesting.adapter.ProductAdapter
import com.appinventiv.mvvmunittesting.api.NetworkResult
import com.appinventiv.mvvmunittesting.databinding.ActivityMainBinding
import com.appinventiv.mvvmunittesting.viewmodels.MainViewModel
import com.appinventiv.mvvmunittesting.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.productList.layoutManager = GridLayoutManager(this, 2)

        val repository = (application as MVVMUTApplication).productRepository
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)
        mainViewModel.getProducts()
        mainViewModel.products.observe(this, Observer {
            when(it){
                is NetworkResult.Success ->
                    binding.productList.adapter = ProductAdapter(it.data!!)
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        })
    }
}