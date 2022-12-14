package com.example.news.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object{
        private var retrofit:Retrofit?=null
        private fun getRetrofitInstance(): Retrofit {
            if (retrofit != null) return retrofit!!;

            val loggingInterceptor = HttpLoggingInterceptor(
                logger = HttpLoggingInterceptor.Logger { message ->
                    Log.e("okhttp", message)
                }
            )
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            retrofit = Retrofit.Builder()
                .client(okHttp)
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit!!;
        }

        fun getService(): WebServices {
            return getRetrofitInstance().create(WebServices::class.java)
        }
    }
}