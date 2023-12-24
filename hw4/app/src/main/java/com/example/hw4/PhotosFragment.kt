package com.example.hw4


import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import com.squareup.picasso.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PhotosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        param1 = args?.getString("title")
        param3 = args?.getString("itemId")
            // call.enqueue(object:Callback<similarphotosResponse>{
            //     override fun onResponse(
            //         call: Call<similarphotosResponse>,
            //         response: Response<similarphotosResponse>
            //     ){
            //         if(response.isSuccessful){
            //             val item = response.body()
            //             val linkList = item?.items?.map { it.link }
            //             if (linkList != null) {
            //                 for(link in linkList){
            //                     Log.i("PhotosFragment", link)
            //                     val imageView = ImageView(context)
            //                     imageView.layoutParams = ViewGroup.LayoutParams(
            //                         ViewGroup.LayoutParams.WRAP_CONTENT,
            //                         ViewGroup.LayoutParams.WRAP_CONTENT
            //                     )

            //                     Picasso.get().load(link).into(imageView)
            //                 }
            //             }
            //         }
            //         else {
            //             Log.i("PhotosFragment", "onResponse: ")
            //         }
            //     }

            //     override fun onFailure(call: Call<similarphotosResponse>, t: Throwable) {
            //         Log.i("PhotosFragment", "onFailure")
            //     }
            // })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        param1 = args?.getString("title")
        val handler = Handler()
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (param1 != null) {
            val ply = view?.findViewById<ConstraintLayout>(R.id.photoslayout)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Log.i("PhotosFragment", param1!!)
            val service = retrofit.create(similarphotosEbayApiService::class.java)
            val call = service.searchItems(param1!!)
            val originalPolicy = StrictMode.getThreadPolicy()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val response = call.execute()
            if (response.isSuccessful) {
                val welcome = view?.findViewById<ConstraintLayout>(R.id.welcome)
                val framelayout = view?.findViewById<FrameLayout>(R.id.framelayout)
                val handler = Handler()
                handler.postDelayed({welcome?.visibility = View.GONE;framelayout?.visibility=View.VISIBLE},200)
                
                val item = response.body()
                val linkList = item?.items?.map { it.link }
                var numberOfImages = 9 // 你想要的 ImageView 的数量
                val imageViews = Array(numberOfImages) { ImageView(context) }
                var i = 0
                val constraintSet = ConstraintSet()
                var prevImageViewId = View.NO_ID
                if (linkList != null) {
                    while(i<numberOfImages&&i<linkList.size){
                        val link = linkList[i]
                        val imageView = ImageView(context)
                        imageView.id = View.generateViewId() // 为每个 ImageView 生成一个唯一的 ID
                        
                        // 设置 ImageView 的布局参数
                        val layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        imageView.layoutParams = layoutParams
                        
                        // 使用 Picasso 或其他图片加载库加载图片到 ImageView
                        Picasso.get().load(link).into(imageView,object:
                            com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                val width = imageView.getDrawable().getIntrinsicWidth()
                                val height = imageView.getDrawable().getIntrinsicHeight()
                                val newlayoutParams = ConstraintLayout.LayoutParams(
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                                );
                                newlayoutParams.dimensionRatio = String.format("%d:%d", width, height);
                                Log.i("PhotosFragment", "onSuccess: "+width+" "+height)
                                imageView.layoutParams = newlayoutParams

                                val iwidth = imageView.layoutParams.width
                                val iheight = imageView.layoutParams.height
                                Log.i("PhotosFragment", "ionSuccess: "+iwidth+" "+iheight)
                                ply?.addView(imageView)

                                // 将 ImageView 与 ConstraintLayout 进行约束
                                constraintSet.clone(ply);
                                // 设置相邻 ImageView 之间的垂直约束，如果有多个 ImageView
                                Log.i("PhotosFragment", "onViewCreated: "+imageView.id+" "+prevImageViewId)
                                if (prevImageViewId != View.NO_ID) {
                                    constraintSet.connect(imageView.id, ConstraintSet.TOP, prevImageViewId, ConstraintSet.BOTTOM, 3)
                                }

                                // 应用约束
                                constraintSet.applyTo(ply)
                                // 更新 prevImageViewId
                                prevImageViewId = imageView.id
                                Log.i("PhotosFragment", link)

                            }
                            override fun onError(e: Exception?) {
                                Log.i("PhotosFragment", "onError: "+e)
                                numberOfImages++
                            }
                        })
                        i++
                    }
                }
            } else {
                Log.i("PhotosFragment", "onResponse: ")
            }
            StrictMode.setThreadPolicy(originalPolicy)
            val imageView3 = view?.findViewById<View>(R.id.imageView3)
            val imageView4 = view?.findViewById<View>(R.id.imageView4)
            imageView3?.setOnClickListener {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://hw5712.wn.r.appspot.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val WishListService = retrofit.create(WishListService::class.java)
                val call = WishListService.savewishlist(param3!!)
                call.enqueue(object : retrofit2.Callback<Void> {
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
                val call = WishListService.deletewishlist(param3!!)
                call.enqueue(object : retrofit2.Callback<Void> {
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
}