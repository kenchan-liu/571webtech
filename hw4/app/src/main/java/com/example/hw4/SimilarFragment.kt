package com.example.hw4

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.widget.Spinner

import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode;
import android.util.Log
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.hw4.similaritemAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SimilarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimilarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        param1 = args?.getString("itemId")
        Log.i("param1", param1.toString())
        if(param1!=null){
            val noresults = view?.findViewById<TextView>(R.id.noresult)
            if (noresults != null) {
                noresults.visibility = View.GONE
            }

            val recyclerView: RecyclerView? = view?.findViewById<RecyclerView>(R.id.simrecyclerView)
            val GridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            if (recyclerView != null) {
                recyclerView.layoutManager = GridLayoutManager
            }
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(similaritemsService::class.java)
            val originalPolicy = StrictMode.getThreadPolicy()

            val policy = ThreadPolicy.Builder().permitAll().build()

            StrictMode.setThreadPolicy(policy)
            val call = service.searchItems(param1.toString())
            val response = call.execute()
            val body = response.body()
            var items = body?.getSimilarItemsResponse?.itemRecommendations?.item
            StrictMode.setThreadPolicy(originalPolicy)

            val spinner = view?.findViewById<Spinner>(R.id.spinner)
            val spinner2 = view?.findViewById<Spinner>(R.id.spinner2)
            var selected = ""
            var orderby = ""
            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected = parent?.getItemAtPosition(position).toString()
                    if(selected!="Default"){
                        spinner2?.visibility = View.VISIBLE
                    }
                    if(orderby=="Ascending"){
                        if(selected=="Product Name"){
                            items=items!!.sortedBy { it.title }
                        }
                        else if(selected=="Days Left"){
                            items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            items=items!!.sortedBy { it.shippingCost.__value__ }
                        }
                    }
                    else if(orderby=="Descending"){
                        if(selected=="Product Name"){
                            items=items!!.sortedByDescending { it.title }
                        }
                        else if(selected=="Days Left"){
                            items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            items=items!!.sortedByDescending { it.shippingCost.__value__ }
                        }
                    }
                    val adapter = similaritemAdapter(items!!)
                    if (recyclerView != null) {
                        recyclerView.adapter = adapter
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selected = "Default"
                }
            }
            spinner2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    
                    orderby = parent?.getItemAtPosition(position).toString()
                    if(orderby=="Ascending"){
                        if(selected=="Product Name"){
                            items=items!!.sortedBy { it.title }
                        }
                        else if(selected=="Days Left"){
                            items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            items=items!!.sortedBy { it.shippingCost.__value__ }
                        }
                    }
                    else if(orderby=="Descending"){
                        if(selected=="Product Name"){
                            items=items!!.sortedByDescending { it.title }
                        }
                        else if(selected=="Days Left"){
                            items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            items=items!!.sortedByDescending { it.shippingCost.__value__ }
                        }
                    }
                    val adapter = similaritemAdapter(items!!)
                    recyclerView?.adapter = adapter

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    orderby = ""
                }
            }


            if(orderby=="Ascending"){
                if(selected=="Product Name"){
                    items=items!!.sortedBy { it.title }
                }
                else if(selected=="Days Left"){
                    items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                }
                else if(selected=="Price"){
                    items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                }
                else if(selected=="Shipping Cost"){
                    items=items!!.sortedBy { it.shippingCost.__value__ }
                }
            }
            else if(orderby=="Descending"){
                if(selected=="Product Name"){
                    items=items!!.sortedByDescending { it.title }
                }
                else if(selected=="Days Left"){
                    items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                }
                else if(selected=="Price"){
                    items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                }
                else if(selected=="Shipping Cost"){
                    items=items!!.sortedByDescending { it.shippingCost.__value__ }
                }
            }
            val adapter = similaritemAdapter(items!!)
            if (recyclerView != null) {
                recyclerView.adapter = adapter
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = arguments
        param1 = args?.getString("itemId")

        return inflater.inflate(R.layout.fragment_similar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(param1!=null){
            val spinner = view.findViewById<Spinner>(R.id.spinner)
            val spinner2 = view.findViewById<Spinner>(R.id.spinner2)
            spinner2.isEnabled = false

            val noresults = view.findViewById<TextView>(R.id.noresult)
            noresults.visibility = View.GONE    
            val recyclerView:RecyclerView = view.findViewById(R.id.simrecyclerView)
            val GridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = GridLayoutManager
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(similaritemsService::class.java)
            val originalPolicy = StrictMode.getThreadPolicy()

            val policy = ThreadPolicy.Builder().permitAll().build()
            
            StrictMode.setThreadPolicy(policy)
            val call = service.searchItems(param1.toString())
            Log.i("url", call.request().url.toString())
            val response = call.execute()
            val handler = Handler()
            val welcome = view?.findViewById<ConstraintLayout>(R.id.welcome)
            val framelayout = view?.findViewById<FrameLayout>(R.id.framelayout)
            handler.postDelayed({welcome?.visibility = View.GONE;framelayout?.visibility=View.VISIBLE},200)
        

            val body = response.body()
            Log.i("body:", body.toString())
            var items = body?.getSimilarItemsResponse?.itemRecommendations?.item
            val originalitems = items
            StrictMode.setThreadPolicy(originalPolicy)

            Log.i("items", items.toString())
            var selected = ""
            if(items!=null){
                if(items.isEmpty()){
                    noresults.visibility = View.VISIBLE
                }
            }
            var orderby = ""
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected = parent?.getItemAtPosition(position).toString()
                    if(selected!="Default"){
                        spinner2.isEnabled = true
                    }

                    if(orderby=="Ascending"){
                        if(selected=="Product Name"){
                            spinner2.isEnabled = true
                            items=items!!.sortedBy { it.title }
                        }
                        else if(selected=="Days Left"){
                            spinner2.isEnabled = true

                            items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            spinner2.isEnabled = true

                            items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            spinner2.isEnabled = true

                            items=items!!.sortedBy { it.shippingCost.__value__ }
                        }
                        else if(selected=="Default"){
                            spinner2.isEnabled = false
                            items = originalitems

                        }
                    }
                    else if(orderby=="Descending"){
                        if(selected=="Product Name"){
                            spinner2.isEnabled = true

                            items=items!!.sortedByDescending { it.title }
                        }
                        else if(selected=="Days Left"){
                            spinner2.isEnabled = true

                            items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                        }
                        else if(selected=="Price"){
                            spinner2.isEnabled = true

                            Log.i("items", items.toString())
                            items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                        }
                        else if(selected=="Shipping Cost"){
                            spinner2.isEnabled = true

                            items=items!!.sortedByDescending { it.shippingCost.__value__ }
                        }
                        else if(selected=="Default"){
                            spinner2.isEnabled = false
                            items = originalitems
                        }
                    }
                    else{
                        if(selected!="Default"){
                            spinner2.isEnabled = true
                            items = originalitems

                        }
                    }
                    val adapter = similaritemAdapter(items!!)
                    recyclerView.adapter = adapter
                    Log.i("selected", selected)
                    Log.i("orderby", orderby)
                    Log.i("items", items.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selected = "Default"
                }
            }


            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        orderby = parent?.getItemAtPosition(position).toString()
                        if(orderby=="Ascending"){
                            if(selected=="Product Name"){
                                items=items!!.sortedBy { it.title }
                            }
                            else if(selected=="Days Left"){
                                items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                            }
                            else if(selected=="Price"){
                                items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                            }
                            else if(selected=="Shipping Cost"){
                                items=items!!.sortedBy { it.shippingCost.__value__ }
                            }
                        }
                        else if(orderby=="Descending"){
                            if(selected=="Product Name"){
                                items=items!!.sortedByDescending { it.title }
                            }
                            else if(selected=="Days Left"){
                                items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                            }
                            else if(selected=="Price"){
                                items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                            }
                            else if(selected=="Shipping Cost"){
                                items=items!!.sortedByDescending { it.shippingCost.__value__ }
                            }
                        }
                        val adapter = similaritemAdapter(items!!)
                        recyclerView.adapter = adapter
                        Log.i("orderby", orderby)
                    }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    orderby = ""
                }
            }

            if(orderby=="Ascending"){
                if(selected=="Product Name"){
                    items=items!!.sortedBy { it.title }
                }
                else if(selected=="Days Left"){
                    items=items!!.sortedBy { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                }
                else if(selected=="Price"){
                    items=items!!.sortedBy { it.buyItNowPrice.__value__ }
                }
                else if(selected=="Shipping Cost"){
                    items=items!!.sortedBy { it.shippingCost.__value__ }
                }
            }
            else if(orderby=="Descending"){
                if(selected=="Product Name"){
                    items=items!!.sortedByDescending { it.title }
                }
                else if(selected=="Days Left"){
                    items=items!!.sortedByDescending { it.timeLeft.substringAfter("P").substringBefore("D").toInt() }
                }
                else if(selected=="Price"){
                    items=items!!.sortedByDescending { it.buyItNowPrice.__value__ }
                }
                else if(selected=="Shipping Cost"){
                    items=items!!.sortedByDescending { it.shippingCost.__value__ }
                }
            }
            val adapter = similaritemAdapter(items!!)
            recyclerView.adapter = adapter
        }
        val imageView3 = view?.findViewById<View>(R.id.imageView3)
        val imageView4 = view?.findViewById<View>(R.id.imageView4)
        imageView3?.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val WishListService = retrofit.create(WishListService::class.java)
            val call = WishListService.savewishlist(param1!!)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.i("materialButton", "onResponse: " + response.body())
                        imageView3.visibility = View.GONE
                        imageView4?.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("materialButton2", "onFailure: ", t)
                }
            })

        }
        imageView4?.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val WishListService = retrofit.create(WishListService::class.java)
            val call = WishListService.deletewishlist(param1!!)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.i("materialButton", "onResponse: " + response.body())
                        imageView4.visibility = View.GONE
                        imageView3?.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("materialButton2", "onFailure: ", t)
                }
            })
        }

    }
}

