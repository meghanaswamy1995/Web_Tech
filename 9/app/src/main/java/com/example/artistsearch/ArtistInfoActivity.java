package com.example.artistsearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ArtistInfoActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_artist_info);


        String artistId = getActivity().getIntent().getStringExtra("artistId");
        String artistName = getActivity().getIntent().getStringExtra("artistName");

        Log.d("newact","message hi");
        Toast.makeText(getContext(),"search for", Toast.LENGTH_SHORT);

        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar!=null){
            actionBar.setTitle(artistName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //getArtistInfo(artistId);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}