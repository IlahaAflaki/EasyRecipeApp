package com.example.easyrecipe.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyrecipe.R
import com.example.easyrecipe.databinding.ActivityMealBinding
import com.example.easyrecipe.databinding.FragmentHomeBinding
import com.example.easyrecipe.db.MealDatabase
import com.example.easyrecipe.fragments.HomeFragment
import com.example.easyrecipe.pojo.Meal
import com.example.easyrecipe.videoModel.MealViewModel
import com.example.easyrecipe.videoModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddFavs.setOnClickListener{
            mealToSave?.let {  
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?=null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal

                binding.tvCategory.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal!!.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions

                youtubeLink = meal.strYoutube.toString()
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddFavs.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddFavs.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}