package com.example.artistsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artistsearch.ui.main.SectionsPagerAdapter;
import com.example.artistsearch.databinding.ActivityArtistInfo2Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import java.util.Map;

public class ArtistInfo extends AppCompatActivity {

    private List<ArtworkData> artwork_list;
private ActivityArtistInfo2Binding binding;
    RecyclerView recyclerView;
    private ArtworkAdapter adapter;
    private List<ArtistData> data_list;
    private String jsonResponse;
    private LinearLayoutManager linearLayoutManager;
    private String artistName="";
    private String artistId="";
    private String artistBday="";
    private String artistDday="";
    private String artistNation="";
    private String artistDescr="";
    private boolean isFav=false;
    private HashSet<ArtistBio> bioSet;


    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 ="switch1";
    private SharedPreferences  mPrefs ;
    //set variables of 'myObject', etc.
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private boolean setFav=false;

    public ProgressBar spinner;
    public TableLayout tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtistSearch);
     binding = ActivityArtistInfo2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

         artistId = getIntent().getStringExtra("artistId");
         artistName = getIntent().getStringExtra("artistName");

        mPrefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        gson = new Gson();
        Map<String,?> keys = mPrefs.getAll();
        Log.d("stored","keySize: "+keys.size());
        for(Map.Entry<String,?> entry : keys.entrySet()){
                if(artistName.equals(entry.getKey())){
                    setFav=true;
                }
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            Log.d("actionBAR", "ACTION BAR NOT NULL");
            actionBar.setTitle(artistName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tableView = (TableLayout)findViewById(R.id.tableLayout);
        spinner =(ProgressBar)findViewById(R.id.bioSpinner);
        if(spinner==null){
            Log.d("spinner","--spinner also null");
        }
        if( tableView==null){
            Log.d("spinner","--table layout also null");
        }

        bioSet = new HashSet<>();
        getArtistInfo(artistId);
        getArtworks(artistId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try{
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.fav_menu, menu);
            MenuItem menuItem1 = (MenuItem) menu.findItem(R.id.star);
            MenuItem menuItem2 = (MenuItem) menu.findItem(R.id.starFilled);
            if(setFav == true){
                menuItem1.setVisible(false);
                menuItem2.setVisible(true);
            }
            menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Log.d("menuuuu","menu item clicked" );
                    ((MenuItem) menu.findItem(R.id.star)).setVisible(false);
                    ((MenuItem) menu.findItem(R.id.starFilled)).setVisible(true);
                    isFav=true;
                    String toastmsg = artistName+" is added to favourites ";
                    makeToast(toastmsg);
                    return true;
                }
            });

            menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Log.d("menuuuu","menu item clicked" );
                    ((MenuItem) menu.findItem(R.id.star)).setVisible(true);
                    ((MenuItem) menu.findItem(R.id.starFilled)).setVisible(false);
                    isFav=false;
                    String toastmsg = artistName+" is removed from favourites ";
                    makeToast(toastmsg);

                    return true;
                }
            });

        }
        catch(Exception nl){
            Log.i("ErrorActivity","Error as a result of:"+nl.getMessage());
        }
        return true;
    }

    private void makeToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        if(isFav==true){
            saveArtistData();
        }
        else{
            removeArtistData();
        }
    }

    private void saveArtistData(){

        ArtistBio artistbio = new ArtistBio(artistId,artistBday,artistDday,artistNation,artistDescr,artistName);
        String json = gson.toJson(artistbio);
        prefsEditor.putString(artistName, json);
        Log.d("stored","items stored");
        prefsEditor.commit();
        prefsEditor.apply();

    }

    private void removeArtistData(){

        Map<String,?> keys = mPrefs.getAll();
            Log.d("stored","keySize: "+keys.size());
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String json =entry.getValue().toString();
            ArtistBio obj = gson.fromJson(json, ArtistBio.class);
//            if(obj != null) {
//                Log.d("stored", "pref - " + obj.getArtistName() + obj.getArtistDday());
//            }
            Log.d("stored",entry.getKey() + ": " +
                    entry.getValue().toString());

        }
        mPrefs.edit().remove(artistName).commit();


       keys = mPrefs.getAll();
        Log.d("stored","keySize: "+keys.size());
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String json =entry.getValue().toString();
            ArtistBio obj = gson.fromJson(json, ArtistBio.class);
//            if(obj != null) {
//                Log.d("stored", "pref - " + obj.getArtistName() + obj.getArtistDday());
//            }
            Log.d("stored",entry.getKey() + ": " +
                    entry.getValue().toString());

        }
//        String json = mPrefs.getString("MyObject", "");
    }

    private void setInfoVariables(String name, String bday, String dday, String nation, String bio){
        artistName=name;
        artistBday = bday;
        artistDday=dday;
        artistDescr=bio;
        artistNation=nation;

    }
//------------------------------------------------------------------- Get Artworks ----------------------------------------------------------------------------------

    private void getArtworks(String artistId){
        artwork_list= new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8080/search/artists/"+artistName+"/";
        String url = "http://192.168.0.137:8080/get/artist/artwork/"+artistId+"/";
        Log.d("test-test","url: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject json2 = response.getJSONObject("_embedded");
                            JSONArray array = json2.getJSONArray("artworks");

                            for (int i = 0; i < array.length(); i++) {

//                                    Log.d("artt",array.getJSONObject(i).getString("id"));
//                                    Log.d("artt",array.getJSONObject(i).getString("title"));
//                                    Log.d("artt",array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));

                                    ArtworkData data = new ArtworkData(array.getJSONObject(i).getString("id"),
                                            array.getJSONObject(i).getString("title"),
                                            array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));
                                    artwork_list.add(data);
                                    Log.d("test-test","list--- : "+artwork_list.toString());

                            }
                            TextView emptyView = (TextView) findViewById(R.id.emptyartwork);
                            if(artwork_list.size()!=0){
                                Log.d("test-test","size of the list: "+artwork_list.size());
                                if(emptyView!=null) {
                                    emptyView.setVisibility(View.GONE);
                                }
                                recyclerView = (RecyclerView) findViewById(R.id.artwork_recycler_view);
                                if(recyclerView!=null) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setNestedScrollingEnabled(false);

                                    adapter = new ArtworkAdapter(ArtistInfo.this, artwork_list);
                                    recyclerView.setAdapter(adapter);

                                }

                            }
                            else{
                                if(recyclerView!=null) {
                                    recyclerView.setVisibility(View.GONE);
                                }
                                emptyView.setVisibility(View.VISIBLE);
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
        queue.add(jsonObjectRequest);


    }
//------------------------------------------------------------------- Get ArtistInfo ----------------------------------------------------------------------------------

    private void getArtistInfo(String artistId) {

        String artistName ="";
        String artistBD="";
        String artistDD="";
        String artistNationality="";
        String artistBio="";

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8080/search/artists/"+artistName+"/";http://appcrsl.wl.r.appspot.com/get/artist/
        String url = "http://192.168.0.137:8080/get/artist/" + artistId + "/";
        Log.d("newact", "url: " + url);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("url",response.toString());

                        try {
                            tableView = (TableLayout)findViewById(R.id.tableLayout);
                            spinner =(ProgressBar)findViewById(R.id.bioSpinner);
                            if(spinner==null){
                                Log.d("spinner","--spinner also null");
                            }
                            if( tableView==null){
                                Log.d("spinner","--table layout also null");
                            }
                            Log.d("newact","name: "+response.getString("name"));
                            Log.d("newact","name: "+response.getString("birthday"));
                            Log.d("newact","name: "+response.getString("deathday"));
                            Log.d("newact","name: "+response.getString("nationality"));
                            Log.d("newact","name: "+response.getString("biography")+".");
//

                            setInfoVariables(response.getString("name"),response.getString("birthday"),response.getString("deathday"),
                                    response.getString("nationality"),response.getString("biography"));

                            if(spinner!=null) {
                                spinner.setVisibility(View.GONE);
                            }
                            if(tableView!=null){
                                tableView.setVisibility(View.VISIBLE);
                            }
                            if(response.getString("name").equals("")){
                                Log.d("tab-test"," null.. ");
                                View row = (View)findViewById(R.id.infoName).getParent();
                                ViewGroup container = ((ViewGroup)row.getParent());
                                container.removeView(row);
                                container.invalidate();

                            }
                            else {
                                TextView name = (TextView)findViewById(R.id.infoName);
                                name.setText(response.getString("name"));
                            }

                            if(response.getString("nationality").equals("")){
                                Log.d("tab-test"," null.. ");
                                View row = (View)findViewById(R.id.infoNationality).getParent();
                                ViewGroup container = ((ViewGroup)row.getParent());
                                container.removeView(row);
                                container.invalidate();

                            }
                            else {
                                TextView nation = (TextView) findViewById(R.id.infoNationality);
                                nation.setText(response.getString("nationality"));
                            }
                            if(response.getString("birthday").equals("")){
                                Log.d("tab-test"," null.. ");
                                View row = (View)findViewById(R.id.infoBirth).getParent();
                                ViewGroup container = ((ViewGroup)row.getParent());
                                container.removeView(row);
                                container.invalidate();

                            }
                            else {
                                TextView bd = (TextView) findViewById(R.id.infoBirth);
                                bd.setText(response.getString("birthday"));
                            }

                            if(response.getString("deathday").equals("")){
                                Log.d("tab-test"," null.. ");
                                View row = (View)findViewById(R.id.infoDeath).getParent();
                                ViewGroup container = ((ViewGroup)row.getParent());
                                container.removeView(row);
                                container.invalidate();

                            }
                            else{
                                TextView dd = (TextView) findViewById(R.id.infoDeath);
                                dd.setText(response.getString("deathday"));
                            }


                            if(response.getString("biography").equals("")){
                                Log.d("tab-test"," null.. ");
                                View row = (View)findViewById(R.id.infoBio).getParent();
                                ViewGroup container = ((ViewGroup)row.getParent());
                                container.removeView(row);
                                container.invalidate();

                            }
                            else{
                                Log.d("tab-test","not null.. ");
                                TextView bio = (TextView) findViewById(R.id.infoBio);
                                bio.setText(response.getString("biography"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("debug", "Error" + error);

                    }
                });
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
