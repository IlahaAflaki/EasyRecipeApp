package com.example.easyrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.easyrecipe.databinding.PopularItemsBinding
import com.example.easyrecipe.pojo.MealsByCategory

class PopularMealsAdapter: RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder>(){
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeals(mealsByCategoryList:ArrayList<MealsByCategory>){
        this.mealsList = mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
          return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
     }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val url = mealsList.getOrNull(position)?.strMealThumb
        holder.binding.imgPopularMealItem.load(url){
            crossfade(true)
        }

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    class PopularMealViewHolder(val binding:PopularItemsBinding): RecyclerView.ViewHolder(binding.root)

}