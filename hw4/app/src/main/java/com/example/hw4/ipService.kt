package com.example.hw4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ipService{
    @GET("json")
    fun getIp(
        @Query("token") key: String
    ): Call<ipResponse>
}