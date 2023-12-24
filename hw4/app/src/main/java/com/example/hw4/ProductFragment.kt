package com.example.hw4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


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
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        param1 = args?.getString("itemId")
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
        return inflater.inflate(R.layout.fragment_product, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        val progressBar = view?.findViewById<View>(R.id.progressBar)
                        val textView = view?.findViewById<View>(R.id.textView)
                        progressBar?.visibility = View.GONE
                        textView?.visibility = View.GONE
                        //val imageView2 = view?.findViewById<ImageView>(R.id.imageView2)
                        //imageView2?.visibility = View.VISIBLE
                        val ImageUrls = item?.Item?.PictureURL
                        val viewPager: ViewPager2? = view?.findViewById(R.id.viewPager)
                        viewPager?.visibility=View.VISIBLE
                        val adapter = ImageUrls?.let { CarouselAdapter(it) }
                        viewPager?.adapter = adapter

                        //Picasso.get().load(firstImageUrl).into(imageView2)
                        val titleView = view?.findViewById<TextView>(R.id.detailtitle)
                        titleView?.visibility = View.VISIBLE
                        val priceView = view?.findViewById<TextView>(R.id.pricecontent)
                        priceView?.visibility = View.VISIBLE
                        val detailprices = view?.findViewById<TextView>(R.id.detailprices)
                        val splitter = view?.findViewById<View>(R.id.splitter)
                        detailprices?.visibility = View.VISIBLE
                        splitter?.visibility = View.VISIBLE
                        var shippingcost:String
                        if(param2=="0.0"){
                            shippingcost = "FREE"
                        }else{
                            shippingcost = "$" + param2
                        }
                        val p = "$" + item?.Item?.ConvertedCurrentPrice?.Value+" with "+shippingcost+" SHIPPING"
                        if (detailprices != null) {
                            detailprices.text = p
                        }
                        val highlights = view?.findViewById<View>(R.id.highlights)
                        highlights?.visibility = View.VISIBLE
                        val priceicon = view?.findViewById<View>(R.id.Priceicon)
                        priceicon?.visibility = View.VISIBLE
                        val splitter2 = view?.findViewById<View>(R.id.splitter2)
                        splitter2?.visibility = View.VISIBLE
                        val brandicon = view?.findViewById<View>(R.id.Brandicon)
                        brandicon?.visibility = View.VISIBLE
                        val brandcontent = view?.findViewById<TextView>(R.id.brandcontent)
                        brandcontent?.visibility = View.VISIBLE
                        val nameValueList = item?.Item?.ItemSpecifics?.NameValueList
                        val brandNameValue: NameValue? = nameValueList?.find { it.Name == "Brand" }
                        Log.i("ProductFragment", "onResponse"+brandNameValue.toString())
                        if (brandNameValue != null) {
                            val brandValueList: List<String> = brandNameValue.Value
                            brandcontent?.text = brandValueList[0]
                        } else {
                            brandcontent?.text = "N/A"
                        }

                        val specifications = view?.findViewById<View>(R.id.Specifications)
                        specifications?.visibility = View.VISIBLE
                        val NameValueList = item?.Item?.ItemSpecifics?.NameValueList
                        val title = item?.Item?.Title
                        val price = item?.Item?.ConvertedCurrentPrice?.Value
                        val pic = item?.Item?.PictureURL
                        val linearlayout = view?.findViewById<LinearLayout>(R.id.linearlayout)
                        linearlayout?.visibility = View.VISIBLE
                        val imageView3 = view?.findViewById<View>(R.id.imageView3)
                        imageView3?.visibility = View.VISIBLE
                        for (text in NameValueList!!) {
                            for(t in text.Value){
                                val textView = TextView(context)
                                textView.text = "· " + t
                                textView.textSize = 18f
                                textView.setPadding(0, 0, 0, 10)
                                linearlayout?.addView(textView)
                            }
                        }
                        priceView?.text = "$" + price
                        titleView?.text = title

                    } else {
                        Log.i("ProductFragment", "onResponse"+response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<itemResponse>, t: Throwable) {
                    Log.i("ProductFragment", "onFailure"+t.message.toString())
                }
            })
        }

    }
}
class CarouselAdapter(private val images: List<String>) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView: ImageView = holder.itemView.findViewById(R.id.imageView)
        Picasso.get().load(images[position]).into(imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
