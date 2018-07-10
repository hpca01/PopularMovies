package com.example.hiren_pc_hp.popularmovies.network;

import com.example.hiren_pc_hp.popularmovies.network.json_schema.Movies;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Result;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Videos;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.reviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    public String popular = "movie/popular?api_key=0dff3be5cf5aefa28a90cd35ef65288c";
    @GET(popular)
    Call<Movies> getPopularMovies();

    public String highestRated = "movie/top_rated?api_key=0dff3be5cf5aefa28a90cd35ef65288c";
    @GET(highestRated)
    Call<Movies> getHighestMovies();

    //TODO add interface for querying movie specific data that is related to the API, also need to make POJO class for it

    public String videoTrailer = "/3/movie/{id}/videos?api_key=0dff3be5cf5aefa28a90cd35ef65288c";
    @GET(videoTrailer)
    Call<Videos> getVideoTrailer(@Path("id") int id);

    public String reviews = "/3/movie/{id}/reviews?api_key=0dff3be5cf5aefa28a90cd35ef65288c";
    @GET(reviews)
    Call<Reviews> getReviews(@Path("id") int id);


}
