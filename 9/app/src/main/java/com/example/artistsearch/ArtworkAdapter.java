package com.example.artistsearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.WindowManager;
import androidx.fragment.app.FragmentActivity;
import   android.content.DialogInterface.OnClickListener;
import android.widget.ProgressBar;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ViewHolder> {
    private Context context;
    private List<ArtworkData> artwork_data;
    private LayoutInflater inflater;
    private ViewGroup mParent;

    //private Activity activity = get

    public ArtworkAdapter(Context context, List<ArtworkData> artwork_data) {
        this.context = context;
        this.artwork_data = artwork_data;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArtworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.artwork_card,parent,false);
        mParent=parent;
        return new ArtworkAdapter.ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkAdapter.ViewHolder holder, int position) {
        final ArtworkData artworkdata = artwork_data.get(position);
        holder.nameView.setText(artworkdata.getArtworkName() );
        String imageUri = artworkdata.getArtworkImage();
        Log.d("test-test",imageUri);

        if(imageUri.equals("/assets/shared/missing_image.png")){

            holder.imageView.setImageResource(R.drawable.artsy_seeklogo_com);
        }
        else {
            Picasso.with(context).load(imageUri).into(holder.imageView);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test-test", "testing onclick "+ artworkdata.getArtworkId());
                String artistId = artworkdata.getArtworkId();

               getCategories(artworkdata.getArtworkId(),view);

            }
        });

    }

    @Override
    public int getItemCount() {
        return artwork_data.size();
    }


    private void getCategories(String artworkId, View view){
       // gene_list= new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        String url = "http://192.168.0.137:8080/get/artwork/"+artworkId+"/";
        Log.d("test-test","url: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject json2 = response.getJSONObject("_embedded");
                            JSONArray array = response.getJSONObject("_embedded").getJSONArray("genes");
                            final Dialog dialog = new Dialog(context);
                            if(array.length()==0){
                                Log.d("nullArray","array is null");
                                dialog.setContentView(R.layout.null_category);
                                dialog.show();
                            }
                            else {
                                Log.d("nartt", array.getJSONObject(0).getString("name"));
                                Log.d("nartt", array.getJSONObject(0).getString("description"));
                                Log.d("nartt", array.getJSONObject(0).getJSONObject("_links").getJSONObject("thumbnail").getString("href"));

                                dialog.setContentView(R.layout.activity_category_window);
                                //dialog.setTitle("Category");

                                TextView text = (TextView) dialog.findViewById(R.id.geneName);
                                text.setText(array.getJSONObject(0).getString("name"));
                                TextView descr = (TextView) dialog.findViewById(R.id.geneDescr);
                                descr.setText(array.getJSONObject(0).getString("description"));
                                ImageView image = (ImageView) dialog.findViewById(R.id.geneImage);
                                if (image != null) {
                                    Picasso.with(context).load(array.getJSONObject(0).getJSONObject("_links").getJSONObject("thumbnail").getString("href")).into(image);
                                }

                                dialog.show();
//
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



    public  static class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;
        public CardView cardview;

        public TextView geneName;
        private Context newcontext = null;

        public ViewHolder(View itemView){
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.artworkName);
            imageView=(ImageView) itemView.findViewById(R.id.artworkImage);
            cardview = (CardView) itemView.findViewById(R.id.artwork_card_view);
            newcontext = itemView.getContext();

            geneName =(TextView) itemView.findViewById(R.id.geneName);

        }


    }
}
