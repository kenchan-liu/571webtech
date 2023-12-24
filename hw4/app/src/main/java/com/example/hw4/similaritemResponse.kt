package com.example.hw4
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class similaritemResponse(
    val getSimilarItemsResponse:getSimilarItemsResponse
):Serializable
data class getSimilarItemsResponse(
    val itemRecommendations:itemRecommendations
):Serializable
data class itemRecommendations(
    val item:List<items>
):Serializable
data class items(
    val itemId:String,
    val title:String,
    val viewItemURL:String,
    val buyItNowPrice:Price,
    val shippingCost:Price,
    val imageURL:String,
    val timeLeft:String
):Serializable