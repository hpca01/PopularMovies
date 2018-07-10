package com.example.hiren_pc_hp.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hiren_pc_hp.popularmovies.database.Result;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Movies;
import com.example.hiren_pc_hp.popularmovies.ui.MovieResponseAdapter;
import com.example.hiren_pc_hp.popularmovies.view.MoviesModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public int menuSwitch = 1;
    private MoviesModel mMoviesModel;
    public TextView text;
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    public MovieResponseAdapter mAdapter;

    private static final int popularMovies = 1;
    private static final int highRatedMovies = 2;
    private static final int favoriteMovies = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (menuSwitch){
            case popularMovies:
                menu.findItem(R.id.popularMoviesMenuItem).setVisible(false);
                menu.findItem(R.id.highestRatedMoviesMenuItem).setVisible(true);
                menu.findItem(R.id.favoriteMovies).setVisible(true);
                break;
            case highRatedMovies:
                menu.findItem(R.id.popularMoviesMenuItem).setVisible(true);
                menu.findItem(R.id.highestRatedMoviesMenuItem).setVisible(false);
                menu.findItem(R.id.favoriteMovies).setVisible(true);
                break;
            case favoriteMovies:
                menu.findItem(R.id.popularMoviesMenuItem).setVisible(true);
                menu.findItem(R.id.highestRatedMoviesMenuItem).setVisible(true);
                menu.findItem(R.id.favoriteMovies).setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo gotta code options menu selection code
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.popularMoviesMenuItem:
                menuSwitch = 1;
                populateUi();
                return true;
            case R.id.highestRatedMoviesMenuItem:
                menuSwitch = 2;
                populateUi();
                return true;
            case R.id.favoriteMovies:
                menuSwitch = 3;
                populateUi();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, DetailActivity.class);

        mRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new MovieResponseAdapter(new ArrayList<com.example.hiren_pc_hp.popularmovies
                .database.Result>(), this, new MovieResponseAdapter.OnItemClicked() {
            @Override
            public void onItemClick(Result result) {
                intent.putExtra("ID", result.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mMoviesModel = ViewModelProviders.of(this).get(MoviesModel.class);


        populateUi();

    }

    void populateUi(){
        mMoviesModel.getResultOb(menuSwitch)
                .observe
                        (this, new Observer<List<com.example.hiren_pc_hp.popularmovies.database.Result>>(){
                            @Override
                            public void onChanged(@Nullable List<com.example.hiren_pc_hp.popularmovies.database.Result> results) {
                                mAdapter.update(results);
                            }
                        });

    }

}
