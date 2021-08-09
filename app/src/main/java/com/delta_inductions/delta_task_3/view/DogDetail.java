package com.delta_inductions.delta_task_3.view;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.Database.FavouritesViewModel;
import com.delta_inductions.delta_task_3.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
public class DogDetail extends AppCompatActivity {
    private static final String TAG = "intent";
    private int position;
    private TextView detail;
    private TextView favtext;
    private ArrayList<Favourites> favouritesArrayList;
    private ImageView dogpic;
    private ImageButton imageButton;
    private ImageButton share;
    private String detailtext;
    private String breedname;
    private String URL;
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
        FavouritesViewModel favouritesViewModel;
        favouritesViewModel = new FavouritesViewModel(getApplication());
        favouritesViewModel.getAllNotes().observe(this, new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                favouritesArrayList = new ArrayList<>(favourites);
                setstar();
            }
        });
        if (getIntent().hasExtra("fav")) {
            imageButton.setVisibility(View.INVISIBLE);
            favtext.setVisibility(View.INVISIBLE);
            share.setVisibility(View.VISIBLE);
            position = getIntent().getIntExtra("position",0);
        }
            detailtext = getIntent().getStringExtra("detailtext");
            detailtext = detailtext.replaceAll("null","Unknown");
            detail.setText(detailtext);
            breedname = getIntent().getStringExtra("breedname");
            Log.d(TAG, "onCreate: " + breedname);
            URL = getIntent().getStringExtra("URL");
            Picasso.get().load(getIntent().getStringExtra("URL")).fit().centerInside().into(dogpic, new Callback() {
                @Override
                public void onSuccess() {
                    progressBardog.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    detail.setText("Sorry error has occured");
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (!isfav) {
                            Toast.makeText(DogDetail.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                            Favourites favourites = new Favourites(detailtext, URL, breedname);
                            favouritesViewModel.insert(favourites);
                            imageButton.setBackgroundResource(R.drawable.ic_baseline_star_24);
                            Log.d(TAG, "onClick: " + isfav);
                        }
                     else {
                            Toast.makeText(DogDetail.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                            favouritesViewModel.delete(favouritesArrayList.get(position));
                            favouritesArrayList.remove(position);
                            imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                            favtext.setText("Press the star to add to favourites");
                            isfav= false;
                        }
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BitmapDrawable bitmapDrawable = ((BitmapDrawable) dogpic.getDrawable());
                    Bitmap bitmap = bitmapDrawable .getBitmap();
                    String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,breedname, null);
                    Uri bitmapUri = Uri.parse(bitmapPath);
                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/jpeg");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey this is my favourite dog breed \n "+detailtext);
                    startActivity(Intent.createChooser(shareIntent,breedname));

                }
            });
    }
                private void setstar() {
                    for (int i = 0; i < favouritesArrayList.size(); i++) {
                        if (favouritesArrayList.get(i).getBreedname().equals(breedname)) {
                            position = i;
                            isfav = true;
                            break;
                        }

                    }
                    if (isfav) {
                        imageButton.setBackgroundResource(R.drawable.ic_baseline_star_24);
                        favtext.setText("Press the star again to remove from favourites");
                    }
                }
            }
