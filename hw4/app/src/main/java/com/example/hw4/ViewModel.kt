package com.example.hw4

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class MyViewModel : ViewModel() {
    private val suggestions = MutableLiveData<List<String>>()

    fun getSuggestions(): LiveData<List<String>> {
        return suggestions
    }

    fun fetchSuggestions(query: String) {
        val suggestionsList = fetchSuggestionsFromNetwork(query)

        suggestions.postValue(suggestionsList)
    }

    private fun fetchSuggestionsFromNetwork(query: String): List<String> {
        val originalPolicy = StrictMode.getThreadPolicy()

        val suggestions: MutableList<String> = ArrayList()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hw5712.wn.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        
        StrictMode.setThreadPolicy(policy)

        val autoservice = retrofit.create(autocompleteService::class.java)
        val call = autoservice.searchItems(query)
        val completeresponse:Response<List<autocompleteResponse>> = call.execute()
        if(completeresponse.isSuccessful) {
            val response = completeresponse.body()
            var optionList = response?.map { it.postalCode }
            if (optionList != null) {
                for (option in optionList) {
                    suggestions.add(option)
                }
            }
        }
        else{
            Log.e("viewModelfetch", "Failed: ")
        }
        StrictMode.setThreadPolicy(originalPolicy)

        return suggestions
    }
}
