package com.example.hw4;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;
import android.widget.Button;



import android.os.StrictMode.ThreadPolicy;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.hw4.EbayApiService;
import com.example.hw4.SearchResponse;

import java.io.IOException;
import java.util.List;

public class SearchShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_show);
        Intent intent = getIntent();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this::onBackButtonClick);
        SearchResponse searchResponse = (SearchResponse) intent.getSerializableExtra("searchResponse");
        if (searchResponse.getFindItemsAdvancedResponse().get(0).getSearchResult().get(0).getItem()==null) {
            CardView cardView = findViewById(R.id.cardviews);
            cardView.setVisibility(View.VISIBLE);
            return;
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false);
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://hw5712.wn.r.appspot.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
        WishListService service = retrofit.create(WishListService.class);
        Call<List<String>> call = service.getwishlist();
        StrictMode.ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Response<List<String>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> wishlist = response.body();
        SearchResult SearchResultList = searchResponse.getFindItemsAdvancedResponse().get(0).getSearchResult().get(0);
        Boolean wlp=Boolean.FALSE;
        @SuppressLint("RestrictedApi") ItemAdapter adapter = new ItemAdapter(this,SearchResultList.getItem(), wishlist,wlp);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        
    }
    private void onBackButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}