package com.ijniclohot.logkarmobilechallenge.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConnection {
    private const val BASE_URL = "http://3e888bc2-6f36-4523-a4b8-9ebaba64d392.mock.pstmn.io/"

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)
}