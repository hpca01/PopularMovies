package com.example.hiren_pc_hp.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;

import com.example.hiren_pc_hp.popularmovies.database.Result;

import java.util.List;

public class MoviesModel extends AndroidViewModel {
    Application mApplication;
    private final LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> popularMovies;
    private final LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> highRatedMovies;
    private final LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> favoriteMovies;
    private LiveData<List<Result>> liveResults;
    public int moviecode;

    public MoviesModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
        popularMovies = MovieRepo.getInstance(mApplication).getMovies(1);
        highRatedMovies = MovieRepo.getInstance(mApplication).getMovies(2);
        favoriteMovies = MovieRepo.getInstance(mApplication).getMovies(3);
    }

    public void query(LifecycleOwner owner, int movieCOde){
        this.moviecode = movieCOde;
        liveResults.removeObservers(owner);
        liveResults = setMovie(movieCOde);

    }

    private LiveData<List<Result>> setMovie(int movieCOde) {
        return MovieRepo.getInstance(mApplication).getMovies(movieCOde);
    }

    public LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> getResultOb(int movieCode) {
        LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> output = null;
        switch(movieCode){
            case 1:
                //popularmovies
                output = popularMovies;
                break;
            case 2:
                //highratedMovies
                output= highRatedMovies;
                break;
            case 3:
                //favoritemovies
                output= favoriteMovies;
                break;
            default://by default it returns popular movies
                output = popularMovies;
        }
        return output;
    }


}

