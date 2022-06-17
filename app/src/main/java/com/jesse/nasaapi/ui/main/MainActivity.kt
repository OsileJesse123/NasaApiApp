package com.jesse.nasaapi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.nasaapi.databinding.ActivityMainBinding
import com.jesse.nasaapi.ui.AstronomyPictureAdapter
import com.jesse.nasaapi.ui.DataFetchingState
import com.jesse.nasaapi.ui.MainViewModel
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
                viewModel.dataFetchingState.collect{
                    state ->
                    when(state){
                        is DataFetchingState.LOADING -> {
                            binding.spinner.visibility = View.VISIBLE
                        }
                        is DataFetchingState.SUCCESSFUL -> {
                            binding.spinner.visibility =View.GONE
                        }
                        is DataFetchingState.FAILED -> {
                            binding.spinner.visibility = View.GONE
                            createToast(state.errorMessage)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.astronomyPicturesFlow.collect {
                    astronomyAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.setAstronomyPicturesTriggerFlow.collect{
                    if(it) {
                        viewModel.setAstronomyPictures()
                    }
                }
            }
        }

        viewModel.refreshAstronomyPictures()
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