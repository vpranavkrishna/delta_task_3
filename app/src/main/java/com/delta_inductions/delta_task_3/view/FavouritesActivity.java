package com.delta_inductions.delta_task_3.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delta_inductions.delta_task_3.Adapters.FavouritesListAdapter;
import com.delta_inductions.delta_task_3.Adapters.Onitemclicklistnerfav;
import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.Database.FavouritesViewModel;
import com.delta_inductions.delta_task_3.R;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity implements Onitemclicklistnerfav {
    private static final String TAG ="fav" ;
    private RecyclerView recyclerView;
    private FavouritesViewModel favouritesViewModel;
    private LinearLayoutManager manager;
    private FavouritesListAdapter favadapter;
    private ArrayList<Favourites> FavouritesList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(FavouritesActivity.this);
        loadingDialog.Loadingalert();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissdialog();
            }
        },1500);
        setContentView(R.layout.activity_favourites);
        recyclerView = findViewById(R.id.favrecyclerview);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        favouritesViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(FavouritesViewModel.class);
        favouritesViewModel.getAllNotes().observe(this, new Observer<List<Favourites>>() {
            @Override
            public void onChanged(List<Favourites> favourites) {
                FavouritesList = new ArrayList(favourites);
               for(int i=0; i<FavouritesList.size();i++)
               {
                   for(int j=0;j<FavouritesList.size();j++)
                   {
                       if(FavouritesList.get(i).getBreedname().equals( FavouritesList.get(j).getBreedname()) && i!=j)
                       {
                           Log.d(TAG, "onChanged: "+i+","+j);
                           favouritesViewModel.delete(FavouritesList.get(j));
                           FavouritesList.remove(j);
                       }
                   }
               }
                favadapter = new FavouritesListAdapter(FavouritesActivity.this,FavouritesList);
                favadapter.setOnitemclicklistenerfav((Onitemclicklistnerfav) FavouritesActivity.this);
                recyclerView.setAdapter(favadapter);
//                Toast.makeText(FavouritesActivity.this, "onchanged", Toast.LENGTH_SHORT).show();
            }
        });
     new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
           int position = viewHolder.getAdapterPosition();
           favouritesViewModel.delete(FavouritesList.get(position));
           FavouritesList.remove(position);
             Toast.makeText(FavouritesActivity.this, "Favourite delted", Toast.LENGTH_SHORT).show();
         }

     }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) searchitem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                breedsListAdapter.getFilter().filter(newText);
                favadapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.menubreed )
            startActivity(new Intent(FavouritesActivity.this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemclickfav(int position) {

        Log.d(TAG, "itemclickfav: called");
        Favourites fav = (Favourites) FavouritesList.get(position);
        Intent intent = new Intent(this,DogDetail.class);
        intent.putExtra("detailtext",fav.getDetailtext());
        intent.putExtra("URL",fav.getImageURL());
        intent.putExtra("breedname",fav.getBreedname());
        intent.putExtra("fav",true);
        intent.putExtra("position",position);
        startActivity(intent);
    }

}