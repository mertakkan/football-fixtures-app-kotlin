package com.example.finalproject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api-football-v1.p.rapidapi.com/v3/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                // arda api key: cc5d069fcfmsh0d5abd1bdb7fafdp1178c9jsn4088888bdf0b
                .header("X-RapidAPI-Key", "cc5d069fcfmsh0d5abd1bdb7fafdp1178c9jsn4088888bdf0b") // Replace with your actual API key
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

