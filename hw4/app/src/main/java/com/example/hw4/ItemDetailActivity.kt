package com.example.hw4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var itemid: String
    private lateinit var shippingcost: String
    private lateinit var position: String
    private lateinit var title: String
    private lateinit var inwishlist: String
    fun onBackButtonClick(view: View) {
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        val intent = intent
        title = intent.getStringExtra("title").toString()
        itemid = intent.getStringExtra("id").toString()
        shippingcost = intent.getStringExtra("shippingcost").toString()
        position = intent.getStringExtra("position").toString()
        inwishlist = intent.getStringExtra("inwishlist").toString()
        Log.i("ItemDetailActivity", inwishlist)
        val imageView3 = findViewById<View>(R.id.imageView3)
        val imageView4 = findViewById<View>(R.id.imageView4)
        if(inwishlist=="true"){
            imageView3.visibility = View.GONE
            imageView4?.visibility = View.VISIBLE
        }
        else{
            imageView4?.visibility = View.GONE
            imageView3.visibility = View.VISIBLE
        }
        imageView3?.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val originalPolicy = StrictMode.getThreadPolicy()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            val WishListService = retrofit.create(WishListService::class.java)
            val call: Call<Void> = WishListService.savewishlist(position)
            Log.i("ItemDetailActivity", "onResponse: "+ call.request().url)
            val titles = title.substring(0,10)
            Toast.makeText(this, titles+"... added to wish list", Toast.LENGTH_SHORT).show()
            val response = call.execute()
            imageView3.visibility = View.GONE
            imageView4?.visibility = View.VISIBLE

            if(response.isSuccessful){
                Log.i("ItemDetailActivity", "onResponse: "+response.body())
            }
            else{
                Log.i("ItemDetailActivity", "onResponse: "+response.body())
            }
            StrictMode.setThreadPolicy(originalPolicy)
        }
        imageView4?.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val originalPolicy = StrictMode.getThreadPolicy()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            val WishListService = retrofit.create(WishListService::class.java)
            val call: Call<Void> = WishListService.deletewishlist(itemid)
            val response = call.execute()
            //log execute url
            val titles = title.substring(0,10)
            Toast.makeText(this, titles+"... removed from wish list", Toast.LENGTH_SHORT).show()
            Log.i("ItemDetailActivity", "onResponse: "+ call.request().url)
            if (response.isSuccessful) {
                Log.i("ItemDetailActivity", "onResponse: " + response.body())
            } else {
                Log.i("ItemDetailActivity", "onResponse:falseees " + response.body())
            }
            imageView4.visibility = View.GONE
            imageView3?.visibility = View.VISIBLE

            StrictMode.setThreadPolicy(originalPolicy)
        }

        val fragmentContainer: FragmentContainerView = findViewById(R.id.fragment_container)
        
        val tabs: TabLayout = findViewById(R.id.tabs)
        val resultitle:TextView = findViewById<TextView>(R.id.resultitle)
        resultitle.text = intent.getStringExtra("title").toString()
        val facebook:ImageView = findViewById(R.id.facebook)
        facebook.setOnClickListener{
            //this.facebookhref = `https://www.facebook.com/sharer/sharer.php?u=${Object.values(response)[4]['ViewItemURLForNaturalSearch']}&quote=Buy ${Object.values(response)[4]['Title']} at $${Object.values(response)[4]['CurrentPrice']['Value']} from link below`;
            val url = "https://www.facebook.com/sharer/sharer.php?u="+intent.getStringExtra("url").toString()+"&quote=Buy "+intent.getStringExtra("title").toString()+" at $"+intent.getStringExtra("price").toString()+" from link below"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        val fragmentManager = supportFragmentManager

        replaceFragment(SimilarFragment(), "SIMILAR")

        replaceFragment(PhotosFragment(),"PHOTOS")

        replaceFragment(ShippingFragment(), "SHIPPING")

        replaceFragment(ProductFragment(), "PRODUCT")

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {replaceFragment(ProductFragment(), "PRODUCT")}
                    1 -> {replaceFragment(ShippingFragment(), "SHIPPING")}
                    2 -> {replaceFragment(PhotosFragment(),"PHOTOS")}
                    3 -> {replaceFragment(SimilarFragment(), "SIMILAR")}
                    // 添加其他Tab的处理逻辑
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(tag=="PRODUCT"){
            val initalArgs = Bundle()
            Log.i("ItemDetailActivity", "replaceFragment"+itemid)
            initalArgs.putString("itemId", itemid)
            initalArgs.putString("shippingcost", shippingcost)
            initalArgs.putString("position",position)
            fragment.arguments = initalArgs
        }
        else if(tag=="SHIPPING"){
            val initalArgs = Bundle()
            initalArgs.putString("itemId", itemid)
            initalArgs.putString("shippingcost", shippingcost)
            initalArgs.putString("position",position)
            fragment.arguments = initalArgs
        }
        else if(tag == "PHOTOS"){
            val initalArgs = Bundle()
            initalArgs.putString("title", title)
            initalArgs.putString("itemId", itemid)
            fragment.arguments = initalArgs
        }
        else if (tag == "SIMILAR"){
            val initalArgs = Bundle()
            Log.i("ItemDetailActivity", "replaceFragmentSIMILAR"+itemid)
            initalArgs.putString("itemId", itemid)
            fragment.arguments = initalArgs
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
        fragmentTransaction.commit()
    }
}