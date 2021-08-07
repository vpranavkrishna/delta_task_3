package com.delta_inductions.delta_task_3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.delta_inductions.delta_task_3.Adapters.Onitemclicklistnerfav;
import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.Database.FavouritesViewModel;
import com.delta_inductions.delta_task_3.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogDetail extends AppCompatActivity {
    private static final String TAG = "intent";
    private TextView detail;
    private TextView favtext;
    private ArrayList<Favourites> favouritesArrayList;
    private ImageView dogpic;
    private ImageButton imageButton;
    private ImageButton share;
    private String detailtext;
    private boolean clickedonce = false;
    private String breedname;
    private String URL;
    private Onitemclicklistnerfav listener;
    private boolean isfav = false;
    private ProgressBar progressBardog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);
        imageButton = findViewById(R.id.fav);
        detail = findViewById(R.id.dogdetail);
        dogpic = findViewById(R.id.dogpic);
        favtext = findViewById(R.id.favtext);
        share = findViewById(R.id.share);
        progressBardog = findViewById(R.id.progressbardetail);
        FavouritesViewModel favouritesViewModel ;
        if(getIntent().hasExtra("fav"))
        {
            imageButton.setVisibility(View.INVISIBLE);
            favtext.setVisibility(View.INVISIBLE);
            share.setVisibility(View.VISIBLE);
        }
        detailtext = getIntent().getStringExtra("detailtext");
        detail.setText(detailtext);
        breedname = getIntent().getStringExtra("breedname");
        Log.d(TAG, "onCreate: "+breedname);
        URL =getIntent().getStringExtra("URL");
        Picasso.get().load(getIntent().getStringExtra("URL")).fit().centerInside().into(dogpic,new Callback() {
            @Override
            public void onSuccess() {
                progressBardog.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                detail.setText("Sorry error has occured");
            }
        });

        favouritesViewModel = new FavouritesViewModel(getApplication());
        favouritesViewModel.getAllNotes().observe(this, new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                favouritesArrayList = new ArrayList<>(favourites);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(!clickedonce) {
                            for (int i = 0; i < favouritesArrayList.size(); i++) {
                                if (favouritesArrayList.get(i).getBreedname().equals( breedname)) {
                                    isfav = true;
                                    Toast.makeText(DogDetail.this, "Check Favourites", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onClick: "+isfav+breedname);
                                    break;
                                }
                            }
                            if (!isfav) {
                                Toast.makeText(DogDetail.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                                Favourites favourites = new Favourites(detailtext, URL, breedname);
                                favouritesViewModel.insert(favourites);
                                Log.d(TAG, "onClick: "+isfav);
                            }
                            else  {
                                Toast.makeText(DogDetail.this, "Check Favourites", Toast.LENGTH_SHORT).show();
                            }
                            clickedonce = true;

                        }
                        else  {
                            Toast.makeText(DogDetail.this, "Check Favourites", Toast.LENGTH_SHORT).show();
                        }

                }


        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, URL);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

    }
    }
