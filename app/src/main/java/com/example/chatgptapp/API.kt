package com.example.chatgptapp

import com.example.chatgptapp.model.CompletionRequest
import com.example.chatgptapp.model.CompletionResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface apiService {
    @Headers("Authorization: Bearer ${APIKEY.apikey}")
    @POST("v1/completions")
    suspend fun getApi(@Body completionResponse: CompletionRequest): Response<CompletionResponse>
}

object API {
    var retrofit = Retrofit.Builder()
        .baseUrl(APIKEY.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val result = retrofit.create(apiService::class.java)

}