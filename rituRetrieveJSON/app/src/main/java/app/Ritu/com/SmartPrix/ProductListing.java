package app.Ritu.com.SmartPrix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.Ritu.com.SmartPrix.VolleyImplementation.AppController;

/**
 * Created by ritu on 12/11/15.
 */
public class ProductListing extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Toolbar toolbar;
    private List<Product> myProducts = new ArrayList<Product>();
    ProgressDialog pDialog;
    LinearLayout progressBarlayout,retryLayout;
    LinearLayout RecyclerViewlayout;
    TextView nullCaseText;
    String category="", sort="", asc="";
    String query = "";
    Spinner SelectCategory, sortby;
    String[] sortList = {"Relevance", "Popularity", "Price(low)", "Price(high)"};
    String[] catList = {"Mobiles", "Tablets", "Laptops", "Cameras", "Memory Cards"};
    EditText search;
    Button goSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);


        /*toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(10);*/

        Intent intent = getIntent();
        try{
            category = intent.getStringExtra("category");
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            query = intent.getStringExtra("query");
        } catch (Exception e){
            e.printStackTrace();
        }


        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        SelectCategory = (Spinner) findViewById(R.id.category);
        search = (EditText) findViewById(R.id.search);
        goSearch = (Button) findViewById(R.id.goSearch);
        sortby = (Spinner) findViewById(R.id.sort);

        recyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter=new ProductListingRecyclerAdapter(this,myProducts);

        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                GetMyProductsList(current_page*10);
            }
        });

        pDialog=new ProgressDialog(ProductListing.this);
        pDialog.setMessage("Loading Products...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(true);

        GetMyProductsList(0);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortby.setAdapter(adapter);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectCategory.setAdapter(catAdapter);

        sortby.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = sortby.getSelectedItemPosition();
                        // Toast.makeText(getApplicationContext(), "You have selected " + warrantyList[+position], Toast.LENGTH_LONG).show();
                        switch (position) {
                            case 0:
                                sort = "relevance";
                                break;
                            case 1:
                                sort = "popularity";
                                break;
                            case 2:
                                sort = "price";
                                asc = "asc";
                                break;
                            case 3:
                                sort = "price";
                                asc = "desc";
                                break;
                            default:
                                sort = "";
                                asc = "";
                        }
                        myProducts.clear();
                        GetMyProductsList(0);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        sort = "";
                        asc = "";
                        myProducts.clear();
                        GetMyProductsList(0);

                    }

                }
        );

        SelectCategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = SelectCategory.getSelectedItemPosition();
                        category = catList[position];
                        myProducts.clear();
                        GetMyProductsList(0);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                        category = "";
                        myProducts.clear();
                        GetMyProductsList(0);

                    }

                }
        );

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = search.getText().toString();
                myProducts.clear();
                GetMyProductsList(0);
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

    public void GetMyProductsList(int start){

        pDialog.show();



        // String getURL=new JsonParser().constructGetUrl(url,params);
        if(query==null) query="";
        if(category==null) category="";
        if(sort==null) sort="";
        if(asc==null) asc="";

        String getURL="http://api.smartprix.com/simple/v1?type=search&key=NVgien7bb7P5Gsc8DWqc&category="
                + category + "&q=" + query + "&indent=1&start=" + String.valueOf(start) + "&sort=" + sort + "&asc=" + asc;
        Log.d("Category URL", getURL);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTxtDisplay.setText("Response: " + response.toString());


                        pDialog.dismiss();







                        try {

                            Log.d("Category response", category + query + sort + asc + response.toString());
                            JSONObject products = response.getJSONObject("request_result");
                            Log.d("ProductListings", products.toString());
                            JSONArray jsonArray = products.getJSONArray("results");

//                        if(jsonArray.length()==0){
//                                recyclerView.setBackgroundResource(R.drawable.empty_product);
//                            }

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject obj = jsonArray.getJSONObject(i);
                                Log.d("Results", obj.toString());
                                Product product =new Product();

                                product.set_Title(obj.getString("name"));
                                product.set_Category(obj.getString("category"));
                                product.set_Price(obj.getInt("price"));
                                product.set_ProductID_and_ImageURL(obj.getString("id"), obj.getString("img_url"));
                                product.set_Brand(obj.getString("brand"));
                                // product.setImageCount(obj.getInt("image_count"));

                                myProducts.add(product);

                            }


                            mAdapter.notifyDataSetChanged();
                            Log.d("my_products", myProducts.toString());
                            // recyclerView.setAdapter(new MyProductsRecyclerAdapter(MyProducts.this,myProducts));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if(myProducts.size()>0) {

                            Log.d("my_products", myProducts.toString());

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