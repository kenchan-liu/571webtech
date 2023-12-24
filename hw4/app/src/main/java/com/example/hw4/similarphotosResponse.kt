package com.example.hw4
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class similarphotosResponse(
    val items:List<photoitem>
):Serializable
data class photoitem(
    val link:String,
    val title:String,
    val snippet:String,
    val htmlTitle:String,
    val htmlSnippet:String,
    val mime:String,
    val displayLink:String
):Serializable