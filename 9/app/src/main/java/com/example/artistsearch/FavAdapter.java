package com.example.artistsearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private List<ArtistBio> fav_artists_data;
    private LayoutInflater inflater;


    public FavAdapter(Context context, List<ArtistBio> artists_data) {
        this.inflater=LayoutInflater.from(context);
        this.context = context;
        this.fav_artists_data = artists_data;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.artist_fav_section,parent,false);
        return new FavAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        final ArtistBio artistdata = fav_artists_data.get(position);
        holder.favnameView.setText(fav_artists_data.get(position).getArtistName() );
        holder.favnationView.setText(fav_artists_data.get(position).getArtistNation() );
        holder.favDateView.setText(fav_artists_data.get(position).getArtistBday() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("test-test", "testing onclick "+ artistdata.getArtistName());
                String artistId = artistdata.getArtistId();
                //Toast.makeText(view.getContext(),artistdata.getArtistId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ArtistInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("artistId",artistId);
                intent.putExtra("artistName",artistdata.getArtistName());
                Log.d("test-tes","calling new activity");
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return fav_artists_data.size();
    }

    public  static class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView favnameView;
        public TextView favnationView;
        public TextView favDateView;


        public ViewHolder(View itemView){
            super(itemView);
            favnameView = (TextView) itemView.findViewById(R.id.favName);
            favnationView=(TextView) itemView.findViewById(R.id.favNation);
            favDateView = (TextView) itemView.findViewById(R.id.favDate);

        }


    }
}
