package com.delta_inductions.delta_task_3.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouritesRepo {
    private Doa doafav;
    private LiveData<List<Favourites>> allNotes;
    public FavouritesRepo(Application application)
    {
        FavouritesDatabase favouritesDatabase = FavouritesDatabase.getInstance(application);
        doafav = favouritesDatabase.doa();
        allNotes = doafav.getAllfavourites();
    }
    public void insert(Favourites favourites)
    {
       new insertfavAsync(doafav).execute(favourites);
    }
    public void delete(Favourites favourites)
    {
          new deletefavAsync(doafav).execute(favourites);
    }

    public LiveData<List<Favourites>> getAllNotes() {
        return allNotes;
    }
    private static class insertfavAsync extends AsyncTask<Favourites,Void,Void>
    { private Doa doa;
       private insertfavAsync (Doa doa)
       {
           this.doa = doa;
       }
        @Override
        protected Void doInBackground(Favourites... favourites) {
            doa.insert(favourites[0]);
            return null;
        }
    }
    private static class deletefavAsync extends AsyncTask<Favourites,Void,Void>
    { private Doa doa;
        private deletefavAsync (Doa doa)
        {
            this.doa = doa;
        }
        @Override
        protected Void doInBackground(Favourites... favourites) {
            doa.delete(favourites[0]);
            return null;
        }
    }
}
