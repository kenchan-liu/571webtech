package com.example.hw4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface EbayApiService {
    //@GET("services/search/FindingService/v1")
    @GET("search")
    fun searchItems(
        @Query("category") category: String,
        @Query("isnew") isnew: String,
        @Query("isused") isused: String,
        @Query("isunspecified") isunspecified: String,
        @Query("isfree") isfree: String,
        @Query("islocal") islocal: String,
        @Query("distance") distance: String,
        @Query("keyword")keyword:String,
        @Query("location") location: String,
        //@QueryMap filters: Map<String, String>
    ): Call<SearchResponse>
}
interface sEbayApiService{
    @GET("simplesearch")
    fun searchItems(
        @Query("category") category: String,
        @Query("isnew") isnew: String,
        @Query("isused") isused: String,
        @Query("isunspecified") isunspecified: String,
        @Query("isfree") isfree: String,
        @Query("islocal") islocal: String,
        @Query("keyword")keyword:String,
    ):Call<SearchResponse>
}
interface itemEbayApiService{
    @GET("item")
    fun searchItems(
        @Query("param") id: String,
    ):Call<itemResponse>    
}
interface similarphotosEbayApiService{
    @GET("similar")
    fun searchItems(
        @Query("param") id: String,
    ):Call<similarphotosResponse>    
}
interface autocompleteService{
    @GET("data")
    fun searchItems(
        @Query("param") data: String,
    ):Call<List<autocompleteResponse>>
}

interface similaritemsService{
    @GET("similaritem")
    fun searchItems(
        @Query("param") data: String,
    ):Call<similaritemResponse>
}