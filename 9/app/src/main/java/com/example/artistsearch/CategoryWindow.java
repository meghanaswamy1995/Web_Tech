package com.example.artistsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import android.widget.Toast;
import android.util.DisplayMetrics;

public class CategoryWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_window);
//        setTheme(android.R.style.Theme_Translucent);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);

//        int width = dm.widthPixels;
//        int height = dm.widthPixels;
//        getWindow().setLayout((int)(width*.9), (int)(height*0.9));


        String artworkId = getIntent().getStringExtra("artworkId");

        Log.d("newact","message hi"+artworkId);

        getCategories(artworkId);

    }

    private void getCategories(String artworkId){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.137:8080/get/artwork/"+artworkId+"/";
        Log.d("test-test","url: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject json2 = response.getJSONObject("_embedded");
                            JSONArray array = response.getJSONObject("_embedded").getJSONArray("genes");
                            Log.d("nartt", array.getJSONObject(0).getString("name"));
                            Log.d("nartt", array.getJSONObject(0).getString("description"));
                            Log.d("nartt", array.getJSONObject(0).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));

                            TextView name = findViewById(R.id.geneName);
                            if(name !=null){
                            name.setText(array.getJSONObject(0).getString("name"));
                            }

                            Toast.makeText(getApplicationContext(), array.getJSONObject(0).getString("name"), Toast.LENGTH_LONG).show();
                            TextView descr = findViewById(R.id.geneDescr);
                            if(descr !=null){
                            descr.setText(array.getJSONObject(0).getString("description"));
                                 }
                            ImageView image = findViewById(R.id.geneImage);
                            if(image!=null){
                                Picasso.with(getApplicationContext()).load(array.getJSONObject(0).getJSONObject("_links").getJSONObject("thumbnail").getString("href")).into(image);

                            }

//                            final Dialog dialog = new Dialog(CategoryWindow.this);
//                            dialog.setContentView(R.layout.category_dialog);
//                            dialog.setTitle("Category...");
//                            TextView text = (TextView) dialog.findViewById(R.id.geneName);
//                            text.setText("hello");
//                            ImageView imagel = (ImageView) dialog.findViewById(R.id.geneImage);
//                            imagel.setImageResource(R.drawable.artsy_seeklogo_com);
//                            dialog.show();

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
        queue.add(jsonObjectRequest);
    }

}