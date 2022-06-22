package com.jesse.nasaapi.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.nasaapi.databinding.ActivityMainBinding
import com.jesse.nasaapi.domain.Resource
import com.jesse.nasaapi.ui.AstronomyPictureAdapter
import com.jesse.nasaapi.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    @Inject lateinit var astronomyAdapter: AstronomyPictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){

                launch {
                    viewModel.getAstronomyPictures().collect {
                            result ->
                        astronomyAdapter.submitList(result.data)

                        binding.spinner.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                        if(result is Resource.Failed)
                            result.error?.message?.let { createToast(it) }
                    }
                }
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding.astronomyRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,
                false)
            adapter = astronomyAdapter
        }
    }

    private fun createToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}