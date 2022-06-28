package com.example.artistsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtistSearch);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                CardView crd = (CardView) findViewById(R.id.roundCardView) ;
               // ImageView crd = (ImageView) findViewById(R.id.artsyimg);
                crd.setVisibility(View.GONE);
                ProgressBar spinn = (ProgressBar)findViewById(R.id.minSpinner) ;
                spinn.setVisibility(View.VISIBLE);
               // Intent intent = new Intent(MainActivity.this,SpinnerActivity.class);
//                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
//                startActivity(intent);
//                finish();
            }
        }, 1000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Intent intent = new Intent(MainActivity.this,SpinnerActivity.class);
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        }, 2000);



    }

}