package com.example.news

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.news.api.ApiManager
import com.example.news.base.BaseActivity
import com.example.news.model.NewsResponse
import com.example.news.model.SourcesResponse
import com.example.news.model.SourcesItem
import com.example.news.ui.news.NewsAdapter

import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : BaseActivity() {
    val apiKey = "5909ae28122a471d8b0c237d5989cb73";
    lateinit var tabLayout: TabLayout
    lateinit var  progressBar: ProgressBar
    lateinit var  recyclerView: RecyclerView
    val adapter= NewsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        //getNewsSources()
    }

    fun initViews(){
        tabLayout = findViewById(R.id.tab_layout);
        progressBar=findViewById(R.id.progress_bar)
        recyclerView=findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        getNewsSources();
    }

    fun getNewsSources() {
        showLoadingDialog();
        ApiManager
            .getService()
            .getNewsSources(apiKey, "" )
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    hideLoading()
                    showMessage("Something went wrong try again",
                        posActionTitle = "tryAgain",
                        posAction = DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                            getNewsSources()
                        });
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    hideLoading()
                    if (response.body()?.status.equals("error")) {
                        showMessage(
                            response.body()?.message ?: "",
                            posActionTitle = "Ok"
                        )
                    } else {
                        showSourcesInTabs(response.body()?.sources);
                    }
                }
            })
    }

    fun getNews(sourceId: String) {
        // progressBar.isVisible =true
        showLoadingDialog()
        ApiManager
            .getService()
            .getNews(apiKey, sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // progressBar.isVisible =false
                    hideLoading()
                    showMessage("Something went wrong try again",
                        posActionTitle = "tryAgain",
                        posAction = DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                            getNews(sourceId)
                        });

                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    hideLoading()
                    // progressBar.isVisible =false
                    if (response.body()?.status.equals("error")) {
                        showMessage(response?.body()?.message ?: "");
                        return
                    }

                    adapter.changeDate(response.body()?.articles)
                    // Todo: show news in recycler view

                }
            })
    }

    fun showSourcesInTabs(sources: List<SourcesItem?>?) {
        if (sources == null) return;
//        for(i in 0..sources.size-1){
//            val item = sources[i];
//        }

        sources.forEach { source ->
            val tab = tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourcesItem
                getNews(source.id!!)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourcesItem
                getNews(source.id!!)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        tabLayout.getTabAt(0)?.select()
    }
}