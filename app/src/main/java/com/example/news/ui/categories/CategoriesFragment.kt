package com.example.news.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Category


class CategoriesFragment: Fragment() {



    val categoriesList= listOf(
        Category( "sports", R.drawable.sports,R.string.sports,R.color.red) ,
        Category( "technology",R.drawable.politics,R.string.technology,R.color.dark_blue),
        Category( "health",R.drawable.health,R.string.health,R.color.pink),
        Category( "bussines",R.drawable.bussines,R.string.bussine,R.color.brown),
        Category( "general",R.drawable.environment,R.string.environment,R.color.blue),
        Category( "science",R.drawable.science,R.string.science,R.color.yellow),
    )
    val adapter = CategoriesAdapter(categoriesList)
    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recycleview_categories)
        recyclerView.adapter=adapter
        adapter.onItemClickListener=object :CategoriesAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, category: Category) {
                onCategoryClickListener?.onCategoryClick(category)
            }
        }
    }

 var onCategoryClickListener:OnCategoryClickListener?=null

    interface OnCategoryClickListener{
        fun onCategoryClick(category: Category)
    }


}