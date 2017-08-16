package app.Ritu.com.SmartPrix;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritu on 12/11/15.
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.MyViewHolder> {

    Context context;

    private List<Prices> myProducts = new ArrayList<Prices>();

    public PriceAdapter(Context cntxt, List<Prices> myproducts) {
        context = cntxt;
        myProducts = myproducts;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.prices_list, parent, false);


        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.productPrice.setText("₹ " + myProducts.get(position).get_Price());
        holder.shop.setText(myProducts.get(position).get_Title());
        holder.Rating.setText("Rating: " + myProducts.get(position).get_Rating());

        Glide.with(context).load(myProducts.get(position).getLogo()).into(holder.productImage);

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(myProducts.get(position).get_link()));
                context.startActivity(viewIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Rating, productPrice, shop;
        ImageView productImage, editProduct;
        Button buy;


        public MyViewHolder(View itemView) {
            super(itemView);
            productPrice = (TextView) itemView.findViewById(R.id.textView2);
            shop = (TextView) itemView.findViewById(R.id.textView1);
            Rating = (TextView) itemView.findViewById(R.id.textView3);
            productImage = (ImageView) itemView.findViewById(R.id.image1);
            buy = (Button) itemView.findViewById(R.id.button1);
        }
    }
}