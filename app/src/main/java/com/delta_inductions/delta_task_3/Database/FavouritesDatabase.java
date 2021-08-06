package com.delta_inductions.delta_task_3.Database;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Favourites.class,version = 1)
public abstract class FavouritesDatabase extends RoomDatabase {
    private static FavouritesDatabase instance;
    public abstract Doa doa();
    public static synchronized FavouritesDatabase getInstance(Context context)
    {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavouritesDatabase.class, "favourites_database")
                    .fallbackToDestructiveMigration().addCallback(roomcallback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };


}
