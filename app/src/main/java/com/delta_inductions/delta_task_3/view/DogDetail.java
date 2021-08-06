package com.delta_inductions.delta_task_3.view;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.delta_inductions.delta_task_3.Adapters.Onitemclicklistnerfav;
import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.Database.FavouritesViewModel;
import com.delta_inductions.delta_task_3.R;
import com.squareup.picasso.Picasso;
public class DogDetail extends AppCompatActivity {
    private static final String TAG = "intent";
    private TextView detail;
    private TextView favtext;
    private ImageView dogpic;
    private ImageButton imageButton;
    private ImageButton share;
    private String detailtext;
    private boolean clickedonce = false;
    private String breedname;
    private String URL;
    private Onitemclicklistnerfav listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);
        imageButton = findViewById(R.id.fav);
        detail = findViewById(R.id.dogdetail);
        dogpic = findViewById(R.id.dogpic);
        favtext = findViewById(R.id.favtext);
        share = findViewById(R.id.share);
        FavouritesViewModel favouritesViewModel = new FavouritesViewModel(getApplication());
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
        Picasso.get().load(getIntent().getStringExtra("URL")).fit().centerInside().into(dogpic);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!clickedonce )
                {
                    if(!getIntent().getBooleanExtra("checkfav",false)) {
                        Toast.makeText(DogDetail.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
//                  listener.transfer(detailtext,URL,breedname);
                        Favourites favourites = new Favourites(detailtext, URL, breedname);
                        favouritesViewModel.insert(favourites);
                    }
                    else
                    {
                        Toast.makeText(DogDetail.this, "Check Favourites", Toast.LENGTH_SHORT).show();
                    }

                }
                clickedonce = true;
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
