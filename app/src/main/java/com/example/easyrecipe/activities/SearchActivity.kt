package com.example.easyrecipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyrecipe.R
import com.example.easyrecipe.adapters.FavsMealsAdapter
import com.example.easyrecipe.databinding.ActivitySearchBinding
import com.example.easyrecipe.pojo.Meal
import com.example.easyrecipe.videoModel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    val mAdapter = FavsMealsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.apply {
            searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.search(it) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

        }

        viewModel.data.observe(this) { data ->
            data?.let {
                mAdapter.differ.submitList(it.meals)
            }
        }

    }

    private fun initViews(){
        binding.searchRV.layoutManager = GridLayoutManager(this,2)
        binding.searchRV.adapter = mAdapter
    }

}