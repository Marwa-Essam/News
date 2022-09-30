package com.example.news.ui.news

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.api.ApiManager
import com.example.news.base.BaseFragment
import com.example.news.model.Category
import com.example.news.model.NewsResponse
import com.example.news.model.SourcesItem
import com.example.news.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment: BaseFragment() {

    companion object{
        fun getInstance(category: Category):NewsFragment{
            val fragment =NewsFragment()
            fragment.category=category
            return fragment

        }
    }
    lateinit var category: Category
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    val apiKey = "5909ae28122a471d8b0c237d5989cb73";
    lateinit var tabLayout: TabLayout
    lateinit var  progressBar: ProgressBar
    lateinit var  recyclerView: RecyclerView
    lateinit var  viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)
        subscribeToLivedata()
        viewModel.getNewsSources(category)

        // getNewsSources()
    }

    private fun subscribeToLivedata() {
        viewModel.progressBarVisible.observe(viewLifecycleOwner, Observer {

            isVisible ->
            progressBar.isVisible =isVisible
        })
        viewModel.sourcesLiveData.observe(viewLifecycleOwner, Observer {
            data ->
            showSourcesInTabs(data)
        })
        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            adapter.changeDate(it)
        })
    }

    val adapter= NewsAdapter(null)
    fun initViews(){
        tabLayout = requireView().findViewById(R.id.tab_layout);
        progressBar=requireView().findViewById(R.id.progress_bar)
        recyclerView=requireView().findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
    }

    fun showSourcesInTabs(sources: List<SourcesItem?>?) {
        if (sources == null) return;
        sources.forEach { source ->
            val tab = tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourcesItem
                viewModel.getNews(source.id!!)
              //  getNews(source.id!!)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourcesItem
               // getNews(source.id!!)
                viewModel.getNews(source.id!!)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        tabLayout.getTabAt(0)?.select()
    }
}