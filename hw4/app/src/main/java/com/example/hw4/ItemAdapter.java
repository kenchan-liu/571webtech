package com.example.hw4;


import java.util.List;

import android.view.Gravity;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;



import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.squareup.picasso.Picasso;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;
// import com.mongodb.ConnectionString;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private com.example.hw4.clicklistener clicklistener;
    private List<Item> items;
    private View item_card;
    private List<String> wishlist;
    private Context context;
    private Boolean wlp;
    private Boolean inwishlist=false;
    //private MongoClient mongoClient;
    //private MongoDatabase database;

    public ItemAdapter(Context context, List<Item> items,List<String> wishlist,Boolean wlp) {
        this.items = items;
        this.context = context;
        this.wishlist = wishlist;
        this.wlp = wlp;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //mongodb+srv://kentlew0jianchen:Ljc20010503@cluster0.unbpvic.mongodb.net/?retryWrites=true&w=majority

        // String connect = "mongodb+srv://kentlew0jianchen:Ljc20010503@cluster0.unbpvic.mongodb.net/?retryWrites=true&w=majority";
        // ConnectionString cs = new ConnectionString(connect);
        // mongoClient = MongoClients.create(cs);
        // database = mongoClient.getDatabase("wishlist");
        item_card = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(item_card);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Item currentItem = items.get(position);
        List<String> galleryUrls = currentItem.getGalleryURL();
        if (galleryUrls != null && !galleryUrls.isEmpty()) {
            String firstImageUrl = galleryUrls.get(0);
            ImageView imageView = item_card.findViewById(R.id.imageView);
            Picasso.get().load(firstImageUrl).into(imageView);
        }
        TextView title = item_card.findViewById(R.id.itemtitle);
        title.setText(currentItem.getTitle().get(0));
        TextView price = item_card.findViewById(R.id.price);
        price.setText(currentItem.getSellingStatus().get(0).getCurrentPrice().get(0).get__value__());
        TextView shipping = item_card.findViewById(R.id.shipping);
        if(currentItem.getShippingInfo().get(0).getShippingType().get(0).equals("Free")||currentItem.getShippingInfo().get(0).getShippingServiceCost()==null){
            shipping.setText("Free");
        }
        else{
            shipping.setText(String.valueOf(currentItem.getShippingInfo().get(0).getShippingServiceCost().get(0).get__value__()));
        }
        TextView condition = item_card.findViewById(R.id.condition);
        TextView zip = item_card.findViewById(R.id.zip);
        if(currentItem.getCondition()!=null&&currentItem.getCondition().get(0).getConditionDisplayName()!=null){
            condition.setText(currentItem.getCondition().get(0).getConditionDisplayName().get(0));
        }
        if(currentItem.getPostalCode()!=null) {
            zip.setText("Zip:" + currentItem.getPostalCode().get(0));
        }
        Button materialButton = item_card.findViewById(R.id.materialButton);
        Button materialButton2 = item_card.findViewById(R.id.materialButton2);
        if(wishlist.contains(currentItem.getItemId().get(0))){
            materialButton.setVisibility(View.GONE);
            materialButton2.setVisibility(View.VISIBLE);
        }
        else{
            materialButton.setVisibility(View.VISIBLE);
            materialButton2.setVisibility(View.GONE);
        }
        materialButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                WishListService service = retrofit.create(WishListService.class);
                Call<Void> call = service.savewishlist(String.valueOf(position));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response){
                        if(response.isSuccessful()){
                            Log.i("materialButton", "onResponse: ");
                            materialButton.setVisibility(View.GONE);
                            materialButton2.setVisibility(View.VISIBLE);
                        }
                        Toast t = Toast.makeText(context, currentItem.getTitle().get(0).substring(0,10)+"... was added to wish list", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER,0,-300);
                        t.show();

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t){
                        Log.i("materialButton", "onFailure: ");
                    }
                });
                inwishlist = true;
            }
        });
        materialButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hw5712.wn.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                WishListService service = retrofit.create(WishListService.class);
                String id = currentItem.getItemId().get(0);
                Call<Void> call = service.deletewishlist(id);
                Log.i("materialButton2", "onClick: "+call.request().url());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response){
                        if(response.isSuccessful()){
                            if(wlp==true){
                                clicklistener.onItemDeleted(id);
                            }
                            Log.i("materialButton2", "onResponse: ");
                            materialButton2.setVisibility(View.GONE);
                            materialButton.setVisibility(View.VISIBLE);
                            Toast t = Toast.makeText(context, currentItem.getTitle().get(0).substring(0,10)+"... was removed from wish list", Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.CENTER,0,-300);
                            t.show();

                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t){
                        Log.e("materialButton2", "onFailure: ",t );
                    }
                });
                inwishlist = false;
            }
 
        });
        item_card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
                String id = currentItem.getItemId().get(0);
                intent.putExtra("id", id);
                String shippingcost = currentItem.getShippingInfo().get(0).getShippingServiceCost().get(0).get__value__();
                intent.putExtra("shippingcost", shippingcost);
                String pos = String.valueOf(position);
                intent.putExtra("position", pos);
                intent.putExtra("title", currentItem.getTitle().get(0));
                intent.putExtra("url", currentItem.getViewItemURL().get(0));
                if(wishlist.contains(currentItem.getItemId().get(0))||inwishlist==true){
                    intent.putExtra("inwishlist", "true");
                }
                else{
                    intent.putExtra("inwishlist", "false");
                }
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnButtonClickListener(clicklistener listener) {
        this.clicklistener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
