package com.example.easyrecipe.videoModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyrecipe.pojo.MealList
import com.example.easyrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val _data = MutableLiveData<MealList>()
    val data get() = _data

    fun search(query: String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.searchMeal(query)
            _data.value = response
        }
    }
}