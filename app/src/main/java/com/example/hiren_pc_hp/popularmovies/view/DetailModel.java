package com.example.hiren_pc_hp.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.hiren_pc_hp.popularmovies.database.Result;


public class DetailModel extends AndroidViewModel {

    private LiveData<Result> details;
    Application mApplication;

    public DetailModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    public LiveData<Result> getDetails(int id) {
        details = MovieRepo.getInstance(mApplication).getMovie(id);
        return details;
    }

    public void markFav(){
        Result res = this.details.getValue();
        boolean isfav = res.isFav();
        if (!isfav){
            //false
            res.setFav(true);
        }else{
            res.setFav(false);
        }
        MovieRepo.getInstance(mApplication).markFav(res);
    }

}
