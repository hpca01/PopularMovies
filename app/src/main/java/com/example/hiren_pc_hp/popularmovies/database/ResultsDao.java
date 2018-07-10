package com.example.hiren_pc_hp.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;


@Dao
public interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertResult(List<Result> results);

    @Query("SELECT * FROM result")
    LiveData<List<Result>> getResults();

    @Query("SELECT * FROM result WHERE movieCode IS :value")
    LiveData<List<Result>> getMovies(int value);

    @Update
    void markFav(Result result);

    @Query("SELECT * FROM result WHERE fav IS 1")
    LiveData<List<Result>> getFavs();

    @Query("SELECT * FROM result WHERE id IS :value")
    LiveData<Result> getbyID(int value);

}
