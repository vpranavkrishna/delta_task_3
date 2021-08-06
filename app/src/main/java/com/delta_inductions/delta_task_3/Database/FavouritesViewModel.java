package com.delta_inductions.delta_task_3.Database;
import android.app.Application;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouritesViewModel extends AndroidViewModel{
 private FavouritesRepo favouritesRepo;
 private LiveData<List<Favourites>> allNotes;
    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        favouritesRepo = new FavouritesRepo(application);
        allNotes = favouritesRepo.getAllNotes();
    }
    public void insert(Favourites favourites)
    {
        favouritesRepo.insert(favourites);
    }
    public void delete(Favourites favourites)
    {
        favouritesRepo.delete(favourites);
    }
    
    public LiveData<List<Favourites>> getAllNotes()
    {
        return allNotes;
    }
}
