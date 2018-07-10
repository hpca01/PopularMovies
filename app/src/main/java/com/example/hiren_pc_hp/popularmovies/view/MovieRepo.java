package com.example.hiren_pc_hp.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import android.os.AsyncTask;


import com.example.hiren_pc_hp.popularmovies.database.ResultDatabase;
import com.example.hiren_pc_hp.popularmovies.database.ResultsDao;
import com.example.hiren_pc_hp.popularmovies.network.DataServiceGen;
import com.example.hiren_pc_hp.popularmovies.network.Service;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Movies;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//TODO this is where the code for input and output of data lives

public class MovieRepo {

    private ResultsDao resultsDao;
    private static MovieRepo movieRepo;

    private static final int popularMovies = 1;
    private static final int highRatedMovies = 2;
    private static final int favoriteMovies = 3;

    private MovieRepo(Application application) {
        ResultDatabase db = ResultDatabase.getDatabase(application.getApplicationContext());
        resultsDao = db.resultsDao();
        //TODO how to use constructor???
    }

    public synchronized static MovieRepo getInstance(Application application) {
        if (movieRepo == null) {
                movieRepo = new MovieRepo(application);
        }
        return movieRepo;
    }

    public void markFav(com.example.hiren_pc_hp.popularmovies.database.Result result){
        new updateAsyncTask(resultsDao).execute(result);
    }

    private void populateDb(Movies movie, int movieCode){
        new insertAsyncTask(resultsDao, movieCode).execute(movie.getResults());
    }

//Todo need to learn how to map the movie repo response to db first then have the data returned to vm
    public LiveData<com.example.hiren_pc_hp.popularmovies.database.Result> getMovie(int id){
        return resultsDao.getbyID(id);
    }

    public LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> getMovies(int movieLoadCode) {

        Service mService = DataServiceGen.getService();
        LiveData<List<com.example.hiren_pc_hp.popularmovies.database.Result>> output = null;

        switch(movieLoadCode){
            case popularMovies:
                mService.getPopularMovies().enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        populateDb(response.body(), popularMovies);
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {


                    }
                });
                output = resultsDao.getMovies(popularMovies);
                break;
            case highRatedMovies:
                mService.getHighestMovies().enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        populateDb(response.body(), highRatedMovies);
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {


                    }
                });
                output = resultsDao.getMovies(highRatedMovies);
                break;
            case favoriteMovies:
                //do something here
                output = resultsDao.getFavs();
                break;

        }

        return output;
    }
    
    private static class insertAsyncTask extends AsyncTask<List<Result>, Void, Void>{
        private ResultsDao mAsyncTaskDao;
        private int movieCode;

        public insertAsyncTask(ResultsDao mAsyncTaskDao, int movieCode) {
            this.mAsyncTaskDao = mAsyncTaskDao;
            this.movieCode = movieCode;
        }


        @Override
        protected Void doInBackground(List<Result>... lists) {
            List<com.example.hiren_pc_hp.popularmovies.database.Result> arrayList
                    = new ArrayList<>();
            for (int i = 0; i < lists[0].size(); i++){
                Result networkRes = lists[0].get(i);
                com.example.hiren_pc_hp.popularmovies.database.Result interm =
                        new com.example.hiren_pc_hp.popularmovies.database.Result();
                interm.setVoteCount(networkRes.getVoteCount());
                interm.setId(networkRes.getId());
                interm.setVideo(networkRes.isVideo());
                interm.setVoteAverage(networkRes.getVoteAverage());
                interm.setTitle(networkRes.getTitle());
                interm.setPopularity(networkRes.getPopularity());
                interm.setPosterPath(networkRes.getPosterPath());
                interm.setOriginalLanguage(networkRes.getOriginalLanguage());
                interm.setOriginalTitle(networkRes.getOriginalTitle());
                interm.setGenreIds(networkRes.getGenreIds());
                interm.setBackdropPath(networkRes.getBackdropPath());
                interm.setAdult(networkRes.isAdult());
                interm.setOverview(networkRes.getOverview());
                interm.setReleaseDate(networkRes.getReleaseDate());
                interm.setMovieCode(this.movieCode);
                arrayList.add(interm);
            }
            mAsyncTaskDao.insertResult(arrayList);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<com.example.hiren_pc_hp.popularmovies.database.Result, Void, Void> {
        private ResultsDao mAsyncTaskDao;

        public updateAsyncTask(ResultsDao resultsDao) {
            mAsyncTaskDao=resultsDao;
        }

        @Override
        protected Void doInBackground(com.example.hiren_pc_hp.popularmovies.database.Result... results) {
            mAsyncTaskDao.markFav(results[0]);
            return null;
        }
    }
}
