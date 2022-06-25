package com.example.artistsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity2 extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    private List<ArtistBio> fav_artistList;
    private LinearLayoutManager linearLayoutManager;
    RecyclerView favRecyclerView;
    FavAdapter favAdapter;
    private boolean spinFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("checkspin","spinFlag1: "+spinFlag);
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtistSearch);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        String date = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
        TextView textViewDate = findViewById(R.id.dateBar);
        textViewDate.setText(date);
        fav_artistList = new ArrayList<>();
        favRecyclerView=findViewById(R.id.favRecyclerView);

        addFavouritesSection();
        //loadSharedPref();

        TextView datebar = (TextView) findViewById(R.id.dateBar);
        TextView favheader = (TextView) findViewById(R.id.favHeader);
        TextView favBar = (TextView) findViewById(R.id.favheader1);
        TextView favBar2 = (TextView) findViewById(R.id.favheader2);
        ProgressBar spinner = (ProgressBar)  findViewById(R.id.mainSpinner);

        Log.d("checkspin","spinFlag2: "+spinFlag);

        if(spinFlag==false) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("checkspin","spinFlag3: "+spinFlag);
                    getSupportActionBar().show();
                    datebar.setVisibility(View.VISIBLE);
                    favheader.setVisibility(View.VISIBLE);
                    favBar.setVisibility(View.VISIBLE);
                    favBar2.setVisibility(View.VISIBLE);
                    favRecyclerView.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                }
            }, 1000);
        }
        else{
            Log.d("checkspin","spinFlag4: "+spinFlag);
            getSupportActionBar().show();
            datebar.setVisibility(View.VISIBLE);
            favheader.setVisibility(View.VISIBLE);
            favBar.setVisibility(View.VISIBLE);
            favBar2.setVisibility(View.VISIBLE);
            favRecyclerView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }
        Button artsy = (Button)findViewById(R.id.artsyButton);
        artsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.artsy.net/";
                //Uri uri = Uri.parse(url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        loadSharedPref();

    }
    private void loadSharedPref(){
        fav_artistList.clear();
        SharedPreferences  mPrefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Gson gson = new Gson();
        Map<String,?> keys = mPrefs.getAll();
        Log.d("stored"," Main keySize: "+keys.size());
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String json =entry.getValue().toString();
            ArtistBio obj = gson.fromJson(json, ArtistBio.class);
            fav_artistList.add(obj);
            Log.d("stored",entry.getKey() + ": " +
                    entry.getValue().toString());
        }
        if(fav_artistList.size()!=0){
            Log.d("fav","artisstlist size: "+fav_artistList.size()+"calling addFav section "+fav_artistList.toString());
            //addFavouritesSection();
        }
        favAdapter.notifyDataSetChanged();
    }


    private void addFavouritesSection(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favRecyclerView.setLayoutManager(linearLayoutManager);
        Log.d("fav"," inside addfev section artisstlist size: "+fav_artistList.size()+"addFav "+fav_artistList.toString());
        favAdapter = new FavAdapter(MainActivity2.this, fav_artistList);
        favRecyclerView.setAdapter(favAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try{

            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            // loadSharedPref();
            SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    openArtistListActivity(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        catch(Exception nl){
            Log.i("ErrorActivity","Error as a result of:"+nl.getMessage());
        }
        return true;
    }

    public void openArtistListActivity(String artistName){
        spinFlag=true;
        Intent intent = new Intent(this,ArtistListActivity.class);

        //Intent intent = new Intent(this,ArtistInfoActivity.class);
        intent.putExtra("artistName",artistName);
        startActivity(intent);
    }

}