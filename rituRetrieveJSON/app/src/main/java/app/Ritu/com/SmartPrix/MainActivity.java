package app.Ritu.com.SmartPrix;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> dataArray = new ArrayList<String>();
    EditText search;
    Button goSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText) findViewById(R.id.search);
        goSearch = (Button) findViewById(R.id.goSearch);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(this, dataArray);
        recyclerView.setAdapter(adapter);
        GetCategoryList();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public void GetCategoryList(){

        // String getURL=new JsonParser().constructGetUrl(url,params);

        String getURL="http://api.smartprix.com/simple/v1?type=categories&key=NVgien7bb7P5Gsc8DWqc&indent=1";
        Log.d("Category URL", getURL);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() { // we sent request

                    @Override
                    public void onResponse(JSONObject response) {    
                        //mTxtDisplay.setText("Response: " + response.toString());

                        try {   //if response didnt come

                            Log.d("Category response", response.toString());
                            JSONArray jsonArray = response.getJSONArray("request_result"); // request_result is key  array is value

//                        if(jsonArray.length()==0){
//                                recyclerView.setBackgroundResource(R.drawable.empty_product);
//                            }

                            for (int i=0;i<jsonArray.length();i++){

                                String obj = jsonArray.getString(i);
                                Log.d("Results", obj);

                                String cat = obj.toString();
                                dataArray.add(cat);
                            }


                            adapter.notifyDataSetChanged();
                            // recyclerView.setAdapter(new MyProductsRecyclerAdapter(MyProducts.this,myProducts));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        AppController.getInstance().addToRequestQueue(jsObjRequest, "category");


    }


}