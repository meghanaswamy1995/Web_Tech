package com.example.artistsearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Window;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<ArtistData> artists_data;
    private LayoutInflater inflater;


    public CustomAdapter(Context context, List<ArtistData> artists_data) {
        this.inflater=LayoutInflater.from(context);
        this.context = context;
        this.artists_data = artists_data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.artist_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ArtistData artistdata = artists_data.get(position);
        holder.nameView.setText(artists_data.get(position).getArtistName() );
        String imageUri = artists_data.get(position).getArtistImage();
        Log.d("test-test",imageUri);

        if(imageUri.equals("/assets/shared/missing_image.png")){

            holder.imageView.setImageResource(R.drawable.artsy_seeklogo_com);
        }
        else {
            Picasso.with(context).load(imageUri).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
       // holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("test-test", "testing onclick "+ artistdata.getArtistName());
                String artistId = artistdata.getArtistId();
                //Toast.makeText(view.getContext(),artistdata.getArtistId(), Toast.LENGTH_SHORT).show();

//                final Dialog dialog = new Dialog(view.getContext());
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.category_dialog);
//                dialog.show();

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
        return artists_data.size();
    }



    public  static class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;
        public CardView cardview;
        private Context newcontext = null;

        public ViewHolder(View itemView){
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
            imageView=(ImageView) itemView.findViewById(R.id.image);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            newcontext = itemView.getContext();
        }


    }
}
