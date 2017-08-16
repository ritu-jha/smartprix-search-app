package app.Ritu.com.SmartPrix;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritu on 12/11/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;

    private List<String> datasrc = new ArrayList<String>();

    public CategoryAdapter(Context cntxt, List<String> myproducts) {
        context = cntxt;
        datasrc = myproducts;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);


        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.Category.setText(datasrc.get(position));


    }

    @Override
    public int getItemCount() {
        return datasrc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Category;


        public MyViewHolder(View itemView) {
            super(itemView);

            Category = (TextView) itemView.findViewById(R.id.list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProductListing.class);
                    int position = getAdapterPosition();
                    i.putExtra("category", datasrc.get(position));
                    context.startActivity(i);
                }
            });

        }
    }
}