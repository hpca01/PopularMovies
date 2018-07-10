package com.example.hiren_pc_hp.popularmovies.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataServiceGen {
    static final String BASE_URL="http://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;

    public static Service getService(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Service.class);
    }
}
