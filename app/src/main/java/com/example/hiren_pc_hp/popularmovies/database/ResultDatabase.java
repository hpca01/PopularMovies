package com.example.hiren_pc_hp.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Result.class}, version = 2, exportSchema = false)
public abstract class ResultDatabase extends RoomDatabase {
    public abstract ResultsDao resultsDao();

    private static ResultDatabase INSTANCE;

    public static ResultDatabase getDatabase(final Context context){
        if(INSTANCE== null){
            synchronized (ResultDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ResultDatabase.class, "result_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
