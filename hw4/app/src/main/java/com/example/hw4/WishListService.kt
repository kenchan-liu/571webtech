package com.example.hw4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WishListService {
    @GET("saveitem")
    fun savewishlist(
        @Query("param") userId: String,
    ): Call<Void>
    @GET("wishlist")
    fun getwishlist(
    ): Call<List<String>>
    @GET("deleteitem")
    fun deletewishlist(
        @Query("param") userId: String,
    ): Call<Void>
    @GET("getwishlist")
    fun getwishitem(
    ): Call<List<Item>>
}