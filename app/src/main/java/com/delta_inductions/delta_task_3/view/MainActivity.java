package com.delta_inductions.delta_task_3.view;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import com.delta_inductions.delta_task_3.Adapters.BreedsSearchAdapter;
import com.delta_inductions.delta_task_3.Adapters.Onitemclicklistener;
import com.delta_inductions.delta_task_3.Adapters.Onitemclicklistnerfav;
import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.R;
import com.delta_inductions.delta_task_3.Adapters.BreedsListAdapter;
import com.delta_inductions.delta_task_3.Model.Breeds;
import com.delta_inductions.delta_task_3.Api.ThedogApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity implements Onitemclicklistener {
    private  RecyclerView recyclerView;
    private ArrayList breedslist = new ArrayList();
    private BreedsListAdapter breedsListAdapter;
    private BreedsSearchAdapter breedsSearchAdapter;
    private ArrayList breedsSearchList;
    private String detailtext;
    String breedname;
    String URL;
    private Retrofit retrofit;
    private FloatingActionButton fab;
    private ThedogApi thedogApi;
    private static final String BASE_URL = "https://api.thedogapi.com/";
    private static final String TAG ="Mainactivity";
    private boolean isScrolling = false;
    private int page =0;
    private ProgressBar progress;
    private Onitemclicklistnerfav listner;
    private ArrayList<Favourites> FavouritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.Loadingalert();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissdialog();
            }
        },1500);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.breedsList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        breedsListAdapter = new BreedsListAdapter(MainActivity.this, breedslist);
        recyclerView.setAdapter(breedsListAdapter);
        breedsListAdapter.setOnitemclicklistenerList((Onitemclicklistener) MainActivity.this);
        thedogApi = getretrofit();
        getAllbreeds();
        getdogsout(0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = manager.getChildCount();
                int totalItems = manager.getItemCount();
                int scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)&&page<=34)
                {
                    isScrolling = false;
                    page = page+1;
                    getdogsout(page);
                    Log.d(TAG, "onScrolled: "+page);

                }
            }
        });

         
    }
    private void getAllbreeds() {
        Call<List<Breeds>> call = thedogApi.getAll();
        call.enqueue(new Callback<List<Breeds>>() {
            @Override
            public void onResponse(Call<List<Breeds>> call, Response<List<Breeds>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse:error " + response.code());
                    return;
                }
                ArrayList<Breeds> breeds = (ArrayList<Breeds>) response.body();
                breedsSearchList = new ArrayList(breeds);
                    breedsSearchAdapter = new BreedsSearchAdapter(MainActivity.this, breedsSearchList);
                    breedsSearchAdapter.setOnitemclicklistenerSearch((Onitemclicklistener) MainActivity.this);
            }

            @Override
            public void onFailure(Call<List<Breeds>> call, Throwable t) {

            }
        });
    }
    private ThedogApi getretrofit() {
        if(retrofit==null)
        {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return (retrofit.create(ThedogApi.class));
    }
    public void getdogsout(int page)
    {
        progress.setVisibility(View.VISIBLE);
        Call<List<Breeds>> call = thedogApi.getBreeds(5,page,"Asc");
           call.enqueue(new Callback<List<Breeds>>() {
            @Override
            public void onResponse(Call<List<Breeds>> call, Response<List<Breeds>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse:error "+response.code());
                    return;
                }
                    ArrayList<Breeds> breeds = (ArrayList<Breeds>) response.body();
                    breedslist.addAll(breeds);
                    breedsListAdapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Breeds>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void itemclickList(int position) {
        data(position);
        Intent intent = new Intent(MainActivity.this,DogDetail.class);
        intent.putExtra("detailtext",detailtext);
        intent.putExtra("URL",URL);
        intent.putExtra("breedname",breedname);
        startActivity(intent);

    }
    @Override
    public void itemclickSearch(int position) {
        data(position);
        Intent intent = new Intent(this,DogDetail.class);
        intent.putExtra("detailtext",detailtext);
        intent.putExtra("URL",URL);
        intent.putExtra("breedname",breedname);
        startActivity(intent);
    }

    private void data (int position)
    {
        Breeds clickeditem = (Breeds) breedsSearchList.get(position);
            detailtext = "Name :"+clickeditem.getName()+"\n"+"Weight :"+clickeditem.getWeight().getMetric()+"Kg"+"\n"+"Height :"+clickeditem.getHeight().getMetric()+"cm"+"\n"+
                    "Bred_for :"+clickeditem.getBred_for()+"\n"+"Breed Group :"+clickeditem.getBreed_group()+"\n"+
                    "Life Span :"+clickeditem.getLife_span()+"\n"+"Temperament :"+clickeditem.getTemperament()+"\n"+"Origin :"+clickeditem.getOrigin();
        URL = String.valueOf(clickeditem.getImage().getUrl());
         breedname = clickeditem.getName();
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView =(androidx.appcompat.widget.SearchView) searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                breedsSearchAdapter.getFilter().filter(newText);
                recyclerView.setAdapter(breedsSearchAdapter);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menufav:
                startActivity(new Intent(this, FavouritesActivity.class));
                break;

            case R.id.upload:
                    startActivity(new Intent(this, ImgaeUploadAndAnalysis.class));
                    break;


        }
        return super.onOptionsItemSelected(item);
    }
}