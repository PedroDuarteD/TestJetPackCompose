package com.pedroduarte.myapplication.Retrofit

import com.pedroduarte.myapplication.data.api.network.service.CardService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor = interceptor)
        .build()

    val api : CardService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CardService.BASE_URL)
        .client(client)
        .build()
        .create(CardService::class.java)
}