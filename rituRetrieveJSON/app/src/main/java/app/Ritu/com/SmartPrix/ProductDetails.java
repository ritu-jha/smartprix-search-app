package app.Ritu.com.SmartPrix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.Ritu.com.SmartPrix.VolleyImplementation.AppController;

/**
 * Created by ritu on 12/11/15.
 */
public class ProductDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private List<Prices> myProducts = new ArrayList<Prices>();
    ProgressDialog pDialog;
    LinearLayout progressBarlayout,retryLayout;
    LinearLayout RecyclerViewlayout;
    TextView ProductName, bestPrice, stores;
    ImageView productImage;
    String Product_id, Pimageurl, productUrl;
    int best = 99999999;
    EditText search;
    Button goSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        /*toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(10);*/


        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        ProductName = (TextView) findViewById(R.id.textView2);
        bestPrice = (TextView) findViewById(R.id.textView3);
        search = (EditText) findViewById(R.id.search);
        goSearch = (Button) findViewById(R.id.goSearch);
        stores = (TextView) findViewById(R.id.textView4);
        productImage = (ImageView) findViewById(R.id.image1);
        Intent intent = getIntent();
        Product_id = intent.getStringExtra("product_id");

        recyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter=new PriceAdapter(this,myProducts);

        recyclerView.setAdapter(mAdapter);

        pDialog=new ProgressDialog(ProductDetails.this);
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(true);



        GetMyProductsList();

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(productUrl));
                startActivity(viewIntent);
            }
        });
        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search.getText().toString();
                Intent i = new Intent(getApplicationContext(), ProductListing.class);
                i.putExtra("query", query);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sign * Method to resolve any signin errors
     */

    public void GetMyProductsList(){

        pDialog.show();



        // String getURL=new JsonParser().constructGetUrl(url,params);

        String getURL="http://api.smartprix.com/simple/v1?type=product_full&key=NVgien7bb7P5Gsc8DWqc&indent=1&id=" + Product_id;
        Log.d("Category URL", getURL);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTxtDisplay.setText("Response: " + response.toString());


                        pDialog.dismiss();

                        try {

                            Log.d("Category response", response.toString());
                            JSONObject products = response.getJSONObject("request_result");
                            Log.d("ProductListings", products.toString());
                            ProductName.setText(products.getString("name"));
                            bestPrice.setText(String.valueOf(products.getInt("price")));
                            best = products.getInt("price");
                            Pimageurl = products.getString("img_url");
                            Glide.with(getApplicationContext()).load(Pimageurl).into(productImage);
                            productUrl = products.getString("link");

                            JSONArray jsonArray = products.getJSONArray("prices");



//                        if(jsonArray.length()==0){
//                                recyclerView.setBackgroundResource(R.drawable.empty_product);
//                            }

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject obj = jsonArray.getJSONObject(i);
                                Log.d("Results", obj.toString());
                                Prices product =new Prices();

                                product.set_Title(obj.getString("store_name"));
                                product.set_Url(obj.getString("store_url"));
                                product.set_Price(obj.getInt("price"));
                                product.set_link(obj.getString("link"));
                                product.set_Rating(obj.getString("store_rating"));
                                product.setLogo(obj.getString("logo"));
                                int tmp = obj.getInt("price");
                                if(tmp<best) best = tmp;

                                // product.setImageCount(obj.getInt("image_count"));

                                myProducts.add(product);

                            }

                            stores.setText("Available at " + String.valueOf(jsonArray.length()) + " stores (best price = " + String.valueOf(best) + ")");


                            mAdapter.notifyDataSetChanged();
                            Log.d("my_products", myProducts.toString());
                            // recyclerView.setAdapter(new MyProductsRecyclerAdapter(MyProducts.this,myProducts));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if(myProducts.size()>0) {

                            Log.d("my_products", myProducts.toString());

                        }else{
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        pDialog.dismiss();


                    }
                });
        AppController.getInstance().addToRequestQueue(jsObjRequest, "category");


    }
}