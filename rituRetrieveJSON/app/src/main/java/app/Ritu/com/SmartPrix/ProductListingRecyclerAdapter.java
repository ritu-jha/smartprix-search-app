package app.Ritu.com.SmartPrix;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritu on 12/11/15.
 */
public class ProductListingRecyclerAdapter extends RecyclerView.Adapter<ProductListingRecyclerAdapter.MyViewHolder> {

    Context context;

    private List<Product> myProducts = new ArrayList<Product>();

    public ProductListingRecyclerAdapter(Context cntxt, List<Product> myproducts) {
        context = cntxt;
        myProducts = myproducts;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item_view, parent, false);


        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.productName.setText(myProducts.get(position).get_Title());
        holder.productPrice.setText("₹ " + myProducts.get(position).get_Price());
        holder.productBrand.setText(myProducts.get(position).get_Brand());

        Glide.with(context).load(myProducts.get(position).get_ImageURL()).into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        return myProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productBrand, productName, productPrice;
        ImageView productImage, editProduct;


        public MyViewHolder(View itemView) {
            super(itemView);

            productBrand = (TextView) itemView.findViewById(R.id.product_brand);

            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProductDetails.class);
                    int position = getAdapterPosition();
                    i.putExtra("product_id", myProducts.get(position).get_ProductID());
                    context.startActivity(i);
                }
            });

        }
    }
}