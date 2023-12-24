package com.example.hw4;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;



class similaritemAdapter(private val items: List<items>) : RecyclerView.Adapter<similaritemAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): similaritemAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.similaritem_card, parent, false)
        return ItemViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: similaritemAdapter.ItemViewHolder, position: Int) {
        val currentItem = items[position]
        val titleview = holder.simtitle
        val priceview = holder.simprice
        val shipview = holder.simshippingprice
        val imageview = holder.imageView
        val timeleft = holder.timeleft
        priceview.text = currentItem.buyItNowPrice.__value__
        titleview.text = currentItem.title
        shipview.text = currentItem.shippingCost.__value__
        val inputString = currentItem.timeLeft
        val startIndex = inputString.indexOf("P") + 1
        val endIndex = inputString.indexOf("D")
        
        if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
            val timeleft = inputString.substring(startIndex, endIndex)
            val timeleftview = holder.timeleft
            timeleftview.text = timeleft+" Days Left"
        }
        Picasso.get().load(currentItem.imageURL).into(imageview)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val simtitle: TextView = itemView.findViewById(R.id.simtitle)
        val simprice: TextView = itemView.findViewById(R.id.simprice)
        val simshippingprice: TextView = itemView.findViewById(R.id.simshippingprice)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val timeleft: TextView = itemView.findViewById(R.id.timeleft)
    }

}
