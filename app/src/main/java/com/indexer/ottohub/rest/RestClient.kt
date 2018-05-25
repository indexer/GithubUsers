package com.indexer.ottohub.rest

import com.suthaw.restaurnat.rest.ApiService
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RestClient private constructor() {
    private val mService: ApiService

    init {
        val restAdapter = Retrofit.Builder().baseUrl(Config.base).addConverterFactory(GsonConverterFactory.create()).client(getNewHttpClient()).build()
        mService = restAdapter.create(ApiService::class.java)
    }

    private fun getNewHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null).apply {
                    addInterceptor(headersInterceptor())
                    addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
                    connectTimeout(10, TimeUnit.SECONDS)
                    followRedirects(true)
                    followSslRedirects(true)
                    writeTimeout(60, TimeUnit.SECONDS)
                    readTimeout(60, TimeUnit.SECONDS)
                    connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                }
        return builder.build()
    }

    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "token " + "39f77c90c7f5e7e490c48f613cc8c31085517a31")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "en")
                .addHeader("Content-Type", "application/json")
                .build())
    }


    companion object {
        private var instance: RestClient? = null
        @Synchronized
        fun getService(): ApiService {
            return getRestInstance().mService
        }

        private fun getRestInstance(): RestClient {
            if (instance == null) {
                instance = RestClient()
            }
            return instance as RestClient
        }
    }
}