package com.example.news.ui.news

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.ApiManager
import com.example.news.model.ArticlesItem
import com.example.news.model.Category
import com.example.news.model.NewsResponse
import com.example.news.model.SourcesItem
import com.example.news.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel() {
    val  sourcesLiveData= MutableLiveData<List<SourcesItem?>?>()
    val progressBarVisible = MutableLiveData(false)
    val  newsList =MutableLiveData<List<ArticlesItem?>?>()
    val apiKey = "5909ae28122a471d8b0c237d5989cb73";

    fun getNewsSources(category: Category) {
        progressBarVisible.value=true
        ApiManager
            .getService()
            .getNewsSources(apiKey,category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBarVisible.value=false
                }
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBarVisible.value=false
                        sourcesLiveData.value=response.body()?.sources
                }
            })
    }

    fun getNews(sourceId: String) {
        progressBarVisible.value=true
        ApiManager
            .getService()
            .getNews(apiKey, sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBarVisible.value=false
                }
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBarVisible.value=false
                    newsList.value=response.body()?.articles
                    // Todo: show news in recycler view
                }
            })
    }


}