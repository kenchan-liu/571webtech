package com.example.hw4

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView


import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Handler
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.easy.view.CircularProgressBar

import com.squareup.picasso.Picasso;

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShippingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShippingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        param1 = args?.getString("itemId")
        
        if(param1!=null){
            val retrofit = Retrofit.Builder()
            .baseUrl("https://hw5712.wn.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            val service = retrofit.create(itemEbayApiService::class.java)
            val call = service.searchItems(param1!!)
            call.enqueue(object : Callback<itemResponse> {
                override fun onResponse(
                    call: Call<itemResponse>,
                    response: Response<itemResponse>
                ) {
                    if (response.isSuccessful) {
                        val item = response.body()
                        Log.i("ShippingFragment", "onResponse"+item?.Item)
                        val storenamec = view?.findViewById<TextView>(R.id.storenamec)
                        storenamec?.text = item?.Item?.Storefront?.StoreName.toString()
                        storenamec?.setOnClickListener{
                            val url = item?.Item?.Storefront?.StoreURL.toString()
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        }
                        val feedbackscorec = view?.findViewById<TextView>(R.id.feedbackscorec)
                        feedbackscorec?.text = item?.Item?.Seller?.FeedbackScore.toString()
                        val progressBar = view?.findViewById<CircularProgressBar>(R.id.progressBar2)
                        val prog = item?.Item?.Seller?.PositiveFeedbackPercent!!.toFloat()
                        Log.i("sss",prog.toString())
                        progressBar?.setProgress(prog)
                        progressBar?.setText(prog.toString()+"%")

                        val feedbackstarc = view?.findViewById<ImageView>(R.id.feedbackstarc)
                        if(item?.Item?.Seller?.FeedbackScore!! <=10000){
                            feedbackstarc?.setImageResource(R.drawable.staroutline)
                        }
                        val colormapping = mutableMapOf<String, String>(
                            Pair("Blue", "#0000FF"),
                            Pair("Green", "#008000"),
                            Pair("GreenShooting","#008000"),
                            Pair("Purple", "#800080"),
                            Pair("PurpleShooting", "#800080"),
                            Pair("Red", "#FF0000"),
                            Pair("RedShooting", "#FF0000"),
                            Pair("SilverShooting", "#C0C0C0"),
                            Pair("Turquoise", "#40E0D0"),
                            Pair("TurquoiseShooting", "#40E0D0"),
                            Pair("Yellow", "#FFFF00"),
                            Pair("YellowShooting", "#FFFF00"),
                        )
                        val starcolor = item?.Item?.Seller?.FeedbackRatingStar!!
                        val drawable = feedbackstarc?.drawable
                        if (drawable != null) {
                            Log.i("ShippingFragment", "onResponse"+starcolor)
                            drawable.setColorFilter(Color.parseColor(colormapping[starcolor]!!), PorterDuff.Mode.SRC_ATOP)
                        }
                        val shippingcostc = view?.findViewById<TextView>(R.id.shippingcostc)
                        if(param2=="0.0"){
                            shippingcostc?.text = "FREE SHIPPING"
                        }else{
                            shippingcostc?.text = "$" + param2 + " SHIPPING"
                        }
                        val globalshippingc = view?.findViewById<TextView>(R.id.globalshippingc)
                        if(item?.Item?.GlobalShipping!!){
                            globalshippingc?.text = "YES"
                        }else{
                            globalshippingc?.text = "NO"
                        }
                        val handlingtimec = view?.findViewById<TextView>(R.id.handlingtimec)
                        handlingtimec?.text = item?.Item?.HandlingTime.toString()
                        val policyc = view?.findViewById<TextView>(R.id.policyc)
                        policyc?.text = item?.Item?.ReturnPolicy?.ReturnsAccepted.toString()
                        val returnwithinc = view?.findViewById<TextView>(R.id.returnwithinc)
                        if(item?.Item?.ReturnPolicy?.ReturnsWithin==""||item?.Item?.ReturnPolicy?.ReturnsWithin==null){
                            returnwithinc?.text = "N/A"
                        }
                        else{
                            returnwithinc?.text = item?.Item?.ReturnPolicy?.ReturnsWithin.toString()
                        }
                        if(item?.Item?.ReturnPolicy?.Refund==""||item?.Item?.ReturnPolicy?.Refund==null){
                            val refundmodec = view?.findViewById<TextView>(R.id.refundmodec)
                            refundmodec?.text = "N/A"
                        }
                        else{
                            val refundmodec = view?.findViewById<TextView>(R.id.refundmodec)
                            refundmodec?.text = item?.Item?.ReturnPolicy?.Refund
                        }
                        if(item?.Item?.ReturnPolicy?.ShippingCostPaidBy==""||item?.Item?.ReturnPolicy?.ShippingCostPaidBy==null){
                            val shippedbyc = view?.findViewById<TextView>(R.id.shippedbyc)
                            shippedbyc?.text = "N/A"
                        }
                        else{
                            val shippedbyc = view?.findViewById<TextView>(R.id.shippedbyc)
                            shippedbyc?.text = item?.Item?.ReturnPolicy?.ShippingCostPaidBy
                        }
                    }
                    else{
                        Log.i("ShippingFragment", "onResponse"+response.code())
                    }
                }

                override fun onFailure(call: Call<itemResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
            val imageView3 = view?.findViewById<View>(R.id.imageView3)
            val imageView4 = view?.findViewById<View>(R.id.imageView4)
            imageView3?.setOnClickListener {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://hw5712.wn.r.appspot.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val WishListService = retrofit.create(WishListService::class.java)
                val call = WishListService.savewishlist(param3!!)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Log.i("materialButton", "onResponse: " + response.body())
                            imageView3.visibility = View.GONE
                            if (imageView4 != null) {
                                imageView4.visibility = View.VISIBLE
                            }
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
                val call = WishListService.deletewishlist(param3!!)
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        param1 = args?.getString("itemId")
        param2 = args?.getString("shippingcost")
        param3 = args?.getString("position")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipping, container, false)
        
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()
        val welcome = view?.findViewById<ConstraintLayout>(R.id.welcome)
        val framelayout = view?.findViewById<FrameLayout>(R.id.framelayout)
        handler.postDelayed({welcome?.visibility = View.GONE;framelayout?.visibility=View.VISIBLE},200)

    }
}