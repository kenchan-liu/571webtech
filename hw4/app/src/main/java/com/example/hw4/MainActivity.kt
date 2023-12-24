package com.example.hw4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Context

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.IOException
interface clicklistener {
    fun onItemDeleted(id:String)
}

class MainActivity : AppCompatActivity() ,clicklistener{
    private lateinit var viewModel: MyViewModel
    private lateinit var wishlist: List<String>
    private lateinit var wishview:RecyclerView
    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        val myTextView3 = findViewById<TextView>(R.id.textview3)
        val myTextView4 = findViewById<TextView>(R.id.textview4)
        val underline0 = findViewById<View>(R.id.underline0)
        val underline1 = findViewById<View>(R.id.underline)
        myTextView3.setOnClickListener {
            underline0.isSelected = true
            underline1.isSelected = false
            val texView = findViewById<TextView>(R.id.textView)
            texView.visibility = View.VISIBLE
            val editTextText = findViewById<TextView>(R.id.editTextText)
            editTextText.visibility = View.VISIBLE
            val textView3 = findViewById<TextView>(R.id.textView3)
            textView3.visibility = View.VISIBLE
            val category = findViewById<Spinner>(R.id.category)
            category.visibility = View.VISIBLE
            val checkBox2 = findViewById<CheckBox>(R.id.checkBox2)
            checkBox2.visibility = View.VISIBLE
            val checkBox3 = findViewById<CheckBox>(R.id.checkBox3)
            checkBox3.visibility = View.VISIBLE
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            checkBox.visibility = View.VISIBLE
            val textView4 = findViewById<TextView>(R.id.textView4)
            textView4.visibility = View.VISIBLE
            val textView5 = findViewById<TextView>(R.id.textView5)
            textView5.visibility = View.VISIBLE
            val checkBox4 = findViewById<CheckBox>(R.id.checkBox4)
            checkBox4.visibility = View.VISIBLE
            val checkBox5 = findViewById<CheckBox>(R.id.checkBox5)
            checkBox5.visibility = View.VISIBLE
            val checkBox6 = findViewById<CheckBox>(R.id.checkBox6)
            checkBox6.visibility = View.VISIBLE
            val textView6 = findViewById<TextView>(R.id.textView6)
            textView6.visibility = View.VISIBLE
            val search_button = findViewById<View>(R.id.search_button)
            search_button.visibility = View.VISIBLE
            val clear_button = findViewById<View>(R.id.clear_button)
            clear_button.visibility = View.VISIBLE
            val textView7 = findViewById<TextView>(R.id.textView7)
            textView7.visibility = View.GONE
            val editTextText3 = findViewById<TextView>(R.id.editTextText3)
            editTextText3.visibility = View.GONE
            val radioButton = findViewById<View>(R.id.radioButton)
            radioButton.visibility = View.GONE
            val radioButton2 = findViewById<View>(R.id.radioButton2)
            radioButton2.visibility = View.GONE
            val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            autoCompleteTextView.visibility = View.GONE
            val textView8 = findViewById<TextView>(R.id.textView8)
            textView8.visibility = View.GONE
            val textView9 = findViewById<TextView>(R.id.textView9)
            textView9.visibility = View.GONE
            wishview = findViewById<RecyclerView>(R.id.wishrecyclerView)
            wishview.visibility = View.GONE
            val cardview = findViewById<CardView>(R.id.cardview)
            cardview.visibility = View.GONE
            val itemstextview = findViewById<TextView>(R.id.items)
            itemstextview.visibility = View.GONE
            val pricesum = findViewById<TextView>(R.id.pricesum)
            pricesum.visibility = View.GONE

        }
        myTextView4.setOnClickListener {
            underline1.isSelected = true
            underline0.isSelected = false
            val texView = findViewById<TextView>(R.id.textView)
            texView.visibility = View.GONE
            val editTextText = findViewById<TextView>(R.id.editTextText)
            editTextText.visibility = View.GONE
            val textView3 = findViewById<TextView>(R.id.textView3)
            textView3.visibility = View.GONE
            val category = findViewById<Spinner>(R.id.category)
            category.visibility = View.GONE
            val checkBox2 = findViewById<CheckBox>(R.id.checkBox2)
            checkBox2.visibility = View.GONE
            val checkBox3 = findViewById<CheckBox>(R.id.checkBox3)
            checkBox3.visibility = View.GONE
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            checkBox.visibility = View.GONE
            val textView4 = findViewById<TextView>(R.id.textView4)
            textView4.visibility = View.GONE
            val textView5 = findViewById<TextView>(R.id.textView5)
            textView5.visibility = View.GONE
            val checkBox4 = findViewById<CheckBox>(R.id.checkBox4)
            checkBox4.visibility = View.GONE
            val checkBox5 = findViewById<CheckBox>(R.id.checkBox5)
            checkBox5.visibility = View.GONE
            val checkBox6 = findViewById<CheckBox>(R.id.checkBox6)
            checkBox6.visibility = View.GONE
            val textView6 = findViewById<TextView>(R.id.textView6)
            textView6.visibility = View.GONE
            val search_button = findViewById<View>(R.id.search_button)
            search_button.visibility = View.GONE
            val clear_button = findViewById<View>(R.id.clear_button)
            clear_button.visibility = View.GONE
            val textView7 = findViewById<TextView>(R.id.textView7)
            textView7.visibility = View.GONE
            val editTextText3 = findViewById<TextView>(R.id.editTextText3)
            editTextText3.visibility = View.GONE
            val radioButton = findViewById<View>(R.id.radioButton)
            radioButton.visibility = View.GONE
            val radioButton2 = findViewById<View>(R.id.radioButton2)
            radioButton2.visibility = View.GONE
            val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            autoCompleteTextView.visibility = View.GONE
            val textView8 = findViewById<TextView>(R.id.textView8)
            textView8.visibility = View.GONE
            val textView9 = findViewById<TextView>(R.id.textView9)
            textView9.visibility = View.GONE
            val wishview = findViewById<RecyclerView>(R.id.wishrecyclerView)
            wishview.visibility = View.VISIBLE
            val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            wishview.layoutManager = layoutManager
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WishListService::class.java)
            val call: Call<List<Item>> = service.getwishitem()
            
            val policy: ThreadPolicy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val response = call.execute()
            val body = response.body()
            val items = body
            val call2: Call<List<String>> = service.getwishlist()

            val response2: Response<List<String>>
            try {
                response2 = call2.execute()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
    
            wishlist = response2.body()!!
            if(wishlist.isEmpty()){
                val cardview = findViewById<CardView>(R.id.cardview)
                cardview.visibility = View.VISIBLE
            }
            else{
                val wlp = true
                val adapter = ItemAdapter(this,items, wishlist,wlp)
                adapter.setOnButtonClickListener(this)
                wishview.adapter = adapter
            }
            val itemstextview = findViewById<TextView>(R.id.items)
            itemstextview.visibility = View.VISIBLE
            
            itemstextview.text = "WishList Total("+wishlist.size.toString()+" items)"
            val pricesum = findViewById<TextView>(R.id.pricesum)
            pricesum.visibility = View.VISIBLE
            if (items != null) {
                if(items.isEmpty()){
                    val itemsv = findViewById<View>(R.id.items)
                    itemsv.visibility = View.GONE
                    pricesum.visibility = View.GONE
                }
            }
    
            var sum = 0.0
            for (item in items!!) {
                sum += item.sellingStatus.get(0).currentPrice.get(0).__value__.toFloat()
            }
            pricesum.text = "$"+String.format("%.2f",sum)

    
        }
        val searchbutton = findViewById<View>(R.id.search_button)
        searchbutton.setOnClickListener {
            performSearch()
        }
        val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
        nearbybutton.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if(isChecked){
                val radioButton = findViewById<View>(R.id.radioButton)
                val radioButton2 = findViewById<View>(R.id.radioButton2)
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.visibility = View.VISIBLE
                    val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
                    autoCompleteTextView.setAdapter(adapter)
                    viewModel.getSuggestions().observe(this, { suggestions ->
                        adapter.clear()
                        suggestions?.let {
                            adapter.addAll(it)
                        }
                        adapter.notifyDataSetChanged()
                    })

                    autoCompleteTextView.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            Log.i("MainActivity",charSequence.toString())
                            viewModel.fetchSuggestions(charSequence.toString())
                        }

                        override fun afterTextChanged(editable: Editable) {
                        }
                    })

                }
                radioButton.visibility = View.VISIBLE
                radioButton2.visibility = View.VISIBLE
                editTextText3.visibility = View.VISIBLE
            }
            else{
                val radioButton = findViewById<View>(R.id.radioButton)
                val radioButton2 = findViewById<View>(R.id.radioButton2)
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                val textView9 = findViewById<TextView>(R.id.textView9)

                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.visibility = View.GONE
                    val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)

                    autoCompleteTextView.setAdapter(adapter)
                    viewModel.getSuggestions().observe(this, { suggestions ->
                        adapter.clear()
                        suggestions?.let {
                            adapter.addAll(it)
                        }
                        Log.i("MainActivity",adapter.toString())
                        adapter.notifyDataSetChanged()
                    })

                    autoCompleteTextView.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            viewModel.fetchSuggestions(charSequence.toString())
                            
                        }

                        override fun afterTextChanged(editable: Editable) {
                        }
                    })

                }
                textView9.visibility = View.GONE
                radioButton.visibility = View.GONE
                radioButton2.visibility = View.GONE
                editTextText3.visibility = View.GONE
            }
        }
        val clearbutton = findViewById<View>(R.id.clear_button)
        clearbutton.setOnClickListener {
            val editTextText = findViewById<TextView>(R.id.editTextText)
            editTextText.text = null
            val spinner = findViewById<Spinner>(R.id.category)
            spinner.setSelection(0)
            val ConditionUsed = findViewById<CheckBox>(R.id.checkBox2)
            ConditionUsed.isChecked = false
            val ConditionNew = findViewById<CheckBox>(R.id.checkBox3)
            ConditionNew.isChecked = false
            val ConditionUnspecified = findViewById<CheckBox>(R.id.checkBox)
            ConditionUnspecified.isChecked = false
            val free = findViewById<CheckBox>(R.id.checkBox5)
            free.isChecked = false
            val local = findViewById<CheckBox>(R.id.checkBox6)
            local.isChecked = false
            val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
            if(nearbybutton.isChecked){
                val radioButton = findViewById<RadioButton>(R.id.radioButton)
                radioButton?.isChecked = false
                val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
                radioButton2?.isChecked = false
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                editTextText3.text = null
                editTextText3.visibility = View.GONE
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                autoCompleteTextView.text = null
                autoCompleteTextView.visibility = View.GONE
                val textView8 = findViewById<TextView>(R.id.textView8)
                textView8.visibility = View.GONE
                val textView9 = findViewById<TextView>(R.id.textView9)
                textView9.visibility = View.GONE
    
            }
            nearbybutton.isChecked = false
        }


        Log.d("ActivityState", "Activity Resumed: ${javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        val handler = Handler()
        handler.postDelayed({setContentView(R.layout.activity_main)},3000)

        Log.d("ActivityState", "Activity Paused: ${javaClass.simpleName}")
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = Handler()
        val layout: ConstraintLayout = findViewById(R.id.normallayout)
        handler.postDelayed({layout.visibility = View.VISIBLE},1000)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        
        val myTextView3 = findViewById<TextView>(R.id.textview3)
        val myTextView4 = findViewById<TextView>(R.id.textview4)
        val underline0 = findViewById<View>(R.id.underline0)
        val underline1 = findViewById<View>(R.id.underline)
        underline0.isSelected = true
        myTextView3.setOnClickListener {
            underline0.isSelected = true
            underline1.isSelected = false
        }
        myTextView4.setOnClickListener {
            underline1.isSelected = true
            underline0.isSelected = false
        }
        val searchbutton = findViewById<View>(R.id.search_button)
        searchbutton.setOnClickListener {
            performSearch()
        }
        val clearbutton = findViewById<View>(R.id.clear_button)
        clearbutton.setOnClickListener {
            val editTextText = findViewById<TextView>(R.id.editTextText)
            editTextText.text = null
            val spinner = findViewById<Spinner>(R.id.category)
            spinner.setSelection(0)
            val ConditionUsed = findViewById<CheckBox>(R.id.checkBox2)
            ConditionUsed.isChecked = false
            val ConditionNew = findViewById<CheckBox>(R.id.checkBox3)
            ConditionNew.isChecked = false
            val ConditionUnspecified = findViewById<CheckBox>(R.id.checkBox)
            ConditionUnspecified.isChecked = false
            val free = findViewById<CheckBox>(R.id.checkBox5)
            free.isChecked = false
            val local = findViewById<CheckBox>(R.id.checkBox6)
            local.isChecked = false
            val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
            if(nearbybutton.isChecked){
                val radioButton = findViewById<RadioButton>(R.id.radioButton)
                radioButton?.isChecked = false
                val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
                radioButton2?.isChecked = false
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                editTextText3.text = null
                editTextText3.visibility = View.GONE
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                autoCompleteTextView.text = null
                autoCompleteTextView.visibility = View.GONE
                val textView8 = findViewById<TextView>(R.id.textView8)
                textView8.visibility = View.GONE
                val textView9 = findViewById<TextView>(R.id.textView9)
                textView9.visibility = View.GONE
    
            }
            nearbybutton.isChecked = false
        }



        val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
        nearbybutton.setOnCheckedChangeListener{
            buttonView, isChecked ->
            if(isChecked){
                val radioButton = findViewById<View>(R.id.radioButton)
                val radioButton2 = findViewById<View>(R.id.radioButton2)
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.visibility = View.VISIBLE
                    autoCompleteTextView.setAdapter(adapter)
                    viewModel.getSuggestions().observe(this, { suggestions ->
                        adapter.clear()
                        suggestions?.let {
                            adapter.addAll(it)
                        }
                        Log.i("MainActivity",adapter.toString())
                        Log.i("MainActivity",suggestions.toString())
                        adapter.notifyDataSetChanged()
                    })

                    autoCompleteTextView.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            viewModel.fetchSuggestions(charSequence.toString())
                        }

                        override fun afterTextChanged(editable: Editable) {
                        }
                    })

                }
                radioButton.visibility = View.VISIBLE
                radioButton2.visibility = View.VISIBLE
                editTextText3.visibility = View.VISIBLE
            }
            else{
                val radioButton = findViewById<View>(R.id.radioButton)
                val radioButton2 = findViewById<View>(R.id.radioButton2)
                val editTextText3 = findViewById<TextView>(R.id.editTextText3)
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.visibility = View.VISIBLE
                    autoCompleteTextView.setAdapter(adapter)
                    viewModel.getSuggestions().observe(this, { suggestions ->
                        adapter.clear()
                        suggestions?.let {
                            adapter.addAll(it)
                        }
                        Log.i("MainActivity",adapter.toString())

                        adapter.notifyDataSetChanged()
                    })

                    autoCompleteTextView.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            viewModel.fetchSuggestions(charSequence.toString())
                        }

                        override fun afterTextChanged(editable: Editable) {
                        }
                    })

                }
                radioButton.visibility = View.GONE
                radioButton2.visibility = View.GONE
                editTextText3.visibility = View.GONE
            }
        }

    }    
    private fun performSearch(){
        val searchtext = findViewById<TextView>(R.id.editTextText)
        val kw = searchtext.text.toString() as String
        if(kw==""){
            val textView8 = findViewById<TextView>(R.id.textView8)
            textView8.visibility = View.VISIBLE
            val toast = Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT)
            toast.show()
            val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
            if (nearbybutton.isChecked){
                val radioButton = findViewById<RadioButton>(R.id.radioButton)
                val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
                val autoCompleteTextView = findViewById<TextView>(R.id.autoCompleteTextView)
                if((radioButton2.isChecked&&autoCompleteTextView.text.toString()=="")||(!radioButton2.isChecked&&!radioButton.isChecked)){
                    val textView9 = findViewById<TextView>(R.id.textView9)
                    textView9.visibility = View.VISIBLE
                    val toast = Toast.makeText(this, "Please enter a valid zipcode", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
    
            return
        }

        val nearbybutton = findViewById<CheckBox>(R.id.checkBox4)
        if (nearbybutton.isChecked){
            val radioButton = findViewById<RadioButton>(R.id.radioButton)
            val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
            val autoCompleteTextView = findViewById<TextView>(R.id.autoCompleteTextView)
            if((radioButton2.isChecked&&autoCompleteTextView.text.toString()=="")||(!radioButton2.isChecked&&!radioButton.isChecked)){
                val textView9 = findViewById<TextView>(R.id.textView9)
                textView9.visibility = View.VISIBLE
                val toast = Toast.makeText(this, "Please enter a valid zipcode", Toast.LENGTH_SHORT)
                toast.show()
                return
            }
        }

        val spinner = findViewById<Spinner>(R.id.category)
        var category=""
        category = spinner.selectedItem.toString()
        Log.i("MainActivity",category)
        val ConditionUsed = findViewById<CheckBox>(R.id.checkBox2)
        val ConditionNew = findViewById<CheckBox>(R.id.checkBox3)
        val ConditionUnspecified = findViewById<CheckBox>(R.id.checkBox)
        val free = findViewById<CheckBox>(R.id.checkBox5)
        val local = findViewById<CheckBox>(R.id.checkBox6)

        if(nearbybutton.isChecked){
            val retrofit = Retrofit.Builder()
            .baseUrl("https://hw5712.wn.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            val service = retrofit.create(EbayApiService::class.java)
            val filtersMap = mutableMapOf<String, String>()
            var j = 0
            if(category!="All") {
                filtersMap["itemFilter(0).name"] = "categoryId"
                filtersMap["itemFilter(0).value"] = category
                j += 1
            }
            Log.i("MainActivity",category)
            var isused = "";
            if (ConditionUsed.isChecked) {
                isused = "true"
            }
            else{
                isused = "false"
            }
            var isnew = "";
            if (ConditionNew.isChecked) {
                isnew = "true"
            }
            else{
                isnew = "false"
            }
            var isunspecified = "";
            if (ConditionUnspecified.isChecked) {
                isunspecified = "true"
            }
            else{
                isunspecified = "false"
            }
            var isfree = "";
            if (free.isChecked) {
                isfree = "true"
            }
            else{
                isfree = "false"
            }
            var islocal = "";
            if (local.isChecked) {
                islocal = "true"
            }
            else{
                islocal = "false"
            }
            var distance = "";
            val milesfrom = findViewById<TextView>(R.id.editTextText3)
            if (nearbybutton.isChecked) {
                distance = milesfrom.text.toString()
            }
            else{
                distance = "10"
            }
            val location = "";
            val retro = Retrofit.Builder()
                .baseUrl("https://ipinfo.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val ipservice = retro.create(ipService::class.java)
            val callip: Call<ipResponse> = ipservice.getIp("5d2fd6027a891c")
            var postalCode = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView).text.toString()
            val originalPolicy = StrictMode.getThreadPolicy()
            val policy = ThreadPolicy.Builder().permitAll().build()
            
            StrictMode.setThreadPolicy(policy)

            val ipres:Response<ipResponse> = callip.execute()
            if(ipres.isSuccessful){
                val ipResponse: ipResponse? = ipres.body()
                postalCode = ipResponse?.postal.toString()
            }
            else{
                Log.e("MainActivity", "Failed: ")
            }
            StrictMode.setThreadPolicy(originalPolicy)

            val call: Call<SearchResponse> = service.searchItems(
                category,
                isnew,
                isused,
                isunspecified,
                isfree,
                islocal,
                distance,
                kw,
                postalCode,
            )
            val url = call.request().url
            Log.e("calling","RequestURL:$url")
            call.enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    //Log.d("MainActivity", "jump to searching")
                    setContentView(R.layout.searching)
                    if (response.isSuccessful) {
                        val searchResponse: SearchResponse? = response.body()
                        //viewModel.setSearchResponse(searchResponse!!)
                        //val searchresult = searchResponse?.findItemsAdvancedResponse?.get(0)?.searchResult?.get(0)?.item
                        val intent = Intent(this@MainActivity, SearchShowActivity::class.java)
                        intent.putExtra("searchResponse", searchResponse)
                        startActivity(intent)

                    } else {
                        Log.d("MainActivity", response.body().toString())
                    }
                }
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e("MainActivity", "Failed: ${t.message}")

                }
            })
        }
        else{
            val retrofit = Retrofit.Builder()
            .baseUrl("https://hw5712.wn.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            val sservice = retrofit.create(sEbayApiService::class.java)
            val filtersMap = mutableMapOf<String, String>()
            var j = 0
            if(category!="All") {
                filtersMap["itemFilter(0).name"] = "categoryId"
                filtersMap["itemFilter(0).value"] = category
                j += 1
            }
            var isused = "";
            if (ConditionUsed.isChecked) {
                isused = "true"
            }
            else{
                isused = "false"
            }
            var isnew = "";
            if (ConditionNew.isChecked) {
                isnew = "true"
            }
            else{
                isnew = "false"
            }
            var isunspecified = "";
            if (ConditionUnspecified.isChecked) {
                isunspecified = "true"
            }
            else{
                isunspecified = "false"
            }
            var isfree = "";
            if (free.isChecked) {
                isfree = "true"
            }
            else{
                isfree = "false"
            }
            var islocal = "";
            if (local.isChecked) {
                islocal = "true"
            }
            else{
                islocal = "false"
            }
            val call: Call<SearchResponse> = sservice.searchItems(
                category,
                isnew,
                isused,
                isunspecified,
                isfree,
                islocal,
                kw
            )
            setContentView(R.layout.searching)
            Log.i("MainActivity","${call.request().url}")
            call.enqueue(object : Callback<SearchResponse> {

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    //Log.d("MainActivity", "jump to searching")

                    if (response.isSuccessful) {
                        val searchResponse: SearchResponse? = response.body()
                        //viewModel.setSearchResponse(searchResponse!!)
                        //val searchresult = searchResponse?.findItemsAdvancedResponse?.get(0)?.searchResult?.get(0)?.item
                        val intent = Intent(this@MainActivity, SearchShowActivity::class.java)
                        intent.putExtra("searchResponse", searchResponse)
                        startActivity(intent)


                    } else {
                        Log.d("MainActivity", response.body().toString())
                    }
                }
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e("MainActivity", "Failed: ${t.message}")

                }
            })

        }

    }

    override fun onItemDeleted(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hw5712.wn.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WishListService::class.java)
        val call: Call<List<Item>> = service.getwishitem()
        
        val policy: ThreadPolicy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val response = call.execute()
        val body = response.body()
        val items = body
        val call2: Call<List<String>> = service.getwishlist()

        val response2: Response<List<String>>
        try {
            response2 = call2.execute()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        wishlist = response2.body()!!
        wishview = findViewById<RecyclerView>(R.id.wishrecyclerView)
        if(wishlist.isEmpty()){
            val wlp = true
            val adapter = ItemAdapter(this,items, wishlist,wlp)
            adapter.setOnButtonClickListener(this)

            wishview.adapter = adapter

            val cardview = findViewById<CardView>(R.id.cardview)
            cardview.visibility = View.GONE
        }
        else{
            val wlp = true
            val adapter = ItemAdapter(this,items, wishlist,wlp)
            adapter.setOnButtonClickListener(this)

            wishview.adapter = adapter
        }
        val pricesum = findViewById<TextView>(R.id.pricesum)
        var sum = 0.0
        for (item in items!!) {
            sum += item.sellingStatus.get(0).currentPrice.get(0).__value__.toFloat()
        }
        val itemstextview = findViewById<TextView>(R.id.items)
        itemstextview.visibility = View.VISIBLE
        
        itemstextview.text = "WishList Total("+wishlist.size.toString()+" items)"

        pricesum.text = "$"+String.format("%.2f",sum)
        if(items.isEmpty()){
            itemstextview.visibility = View.GONE
            pricesum.visibility = View.GONE
        }

        
    }

}