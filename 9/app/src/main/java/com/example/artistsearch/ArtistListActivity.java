package com.example.artistsearch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import java.util.Date;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class ArtistListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<ArtistData> data_list;
    private String jsonResponse;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private String prevSearch="";
    ProgressBar spinner;
    TextView spinenrtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtistSearch);
        setContentView(R.layout.activity_artist_list);
        String artistName = getIntent().getStringExtra("artistName");
        if(artistName == null){
            artistName=prevSearch;
            Log.d("test-test","artistName is null "+ prevSearch);
        }
        else{

            prevSearch=artistName;
            Log.d("test-test","artistName is not null, setting prevSearch "+ prevSearch);
        }

         spinner = (ProgressBar) findViewById(R.id.listSpinner);
        spinenrtext = (TextView) findViewById(R.id.spinnerText);
        String actionName;
        if(artistName!=null) {
             actionName = artistName.toUpperCase();
        }
        else{
            actionName=artistName;
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(actionName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        Log.i("debug","message "+artistName);
                //Toast.makeText(ArtistListActivity.this,"search for", Toast.LENGTH_SHORT);
                getArtists(artistName);
                Log.d("test-test","list size: "+data_list.toString());
               Log.d("test-test","here already...");
    }

    private void getArtists(String artistName){

        data_list = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8080/search/artists/"+artistName+"/";
        String url = "http://192.168.0.137:8080/search/artists/"+artistName+"/";
        Log.d("test-test","url: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("url",response.toString());
                            jsonResponse=response.toString();
                            JSONObject json2 = response.getJSONObject("_embedded");
                            JSONArray array = json2.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {

                                if (array.getJSONObject(i).getString("og_type").equals("artist")) {

                                    Log.d("test-test",array.getJSONObject(i).getJSONObject("_links").getJSONObject("self").getString("href"));
                                    Log.d("test-test",array.getJSONObject(i).getString("title"));
                                    Log.d("test-test",array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));

                                    String val=array.getJSONObject(i).getJSONObject("_links").getJSONObject("self").getString("href");
                                    int newVal = val.lastIndexOf('/');
                                    String artid =val.substring(newVal + 1);
                                    Log.d("test-test", "artid: "+artid);
                                    ArtistData data = new ArtistData(artid,
                                            array.getJSONObject(i).getString("title"),
                                            array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));
                                    data_list.add(data);
                                    Log.d("test-test","list--- : "+data_list.toString());
                                }
                            }
                            TextView emptyView = (TextView) findViewById(R.id.emptyartist);
                            if(data_list.size()!=0){
                                spinner.setVisibility(View.GONE);
                                spinenrtext.setVisibility(View.GONE);
                                Log.d("test-test","size of the list: "+data_list.size());
                                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                //gridLayoutManager = new GridLayoutManager(ArtistListActivity.this,2);
                                //recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setNestedScrollingEnabled(false);

                                adapter = new CustomAdapter(getApplicationContext(), data_list);
                                recyclerView.setAdapter(adapter);
                                //adapter.notifyDataSetChanged();
                            }
                            else{
                                if(recyclerView!=null) {
                                    recyclerView.setVisibility(View.GONE);
                                    spinner.setVisibility(View.GONE);
                                    spinenrtext.setVisibility(View.GONE);

                                }
                                spinner.setVisibility(View.GONE);
                                emptyView.setVisibility(View.VISIBLE);
                                spinenrtext.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("debug","Error"+error);

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("backtest", "testing back press");
    };
}