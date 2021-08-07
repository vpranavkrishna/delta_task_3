package com.delta_inductions.delta_task_3.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Doa {
    @Insert
    void insert(Favourites fav);
    @Delete
    void delete(Favourites fav);
    @Query("select * from favourites_table order by Id")
    LiveData<List<Favourites>> getAllfavourites();
}
