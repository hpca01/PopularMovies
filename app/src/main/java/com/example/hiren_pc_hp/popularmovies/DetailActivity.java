package com.example.hiren_pc_hp.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiren_pc_hp.popularmovies.database.Result;
import com.example.hiren_pc_hp.popularmovies.network.DataServiceGen;
import com.example.hiren_pc_hp.popularmovies.network.Service;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Video;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.Videos;
import com.example.hiren_pc_hp.popularmovies.network.json_schema.reviews.Reviews;
import com.example.hiren_pc_hp.popularmovies.view.DetailModel;
import com.example.hiren_pc_hp.popularmovies.view.MoviesModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    @BindView(value = R.id.movieTitle)
    TextView title;
    @BindView(value = R.id.movieReleasteDate)
    TextView date;
    @BindView(value = R.id.description)
    TextView description;
    @BindView(value = R.id.voteAverage)
    TextView average;
    @BindView(value = R.id.detail_image)
    ImageView posterImage;
    @BindView(R.id.switch1)
    Switch favoriteSwitch;
    @BindView(R.id.trailer)
    Button trailer;
    @BindView(R.id.reviews)
    TextView reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Integer movieID;
        movieID = getIntent().getIntExtra("ID", 0);
        loadUi(movieID, this);

    }

    private void loadUi(int id, final Context context) {
        final Service mService = DataServiceGen.getService();

        final DetailModel model = ViewModelProviders.of(this).get(DetailModel.class);

        model.getDetails(id).observe(this, new Observer<Result>() {

            @Override
            public void onChanged(@Nullable final Result result) {
                favoriteSwitch.setOnCheckedChangeListener(null);
                favoriteSwitch.setChecked(result.isFav());
                favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        model.markFav();
                    }
                });
                title.setText(result.getTitle());
                date.setText(getResources().getText(R.string.ReleaseDateDetail)+result.getReleaseDate());
                description.setText(result.getOverview());
                trailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mService.getVideoTrailer(result.getId()).enqueue(new Callback<Videos>() {
                            @Override
                            public void onResponse(Call<Videos> call, Response<Videos> response) {
                                if(response.isSuccessful()) {
                                    Log.d("response results", String.valueOf(response.body().getResults().size()));
                                    List<Video> reponseres = response.body().getResults();
                                    for (Video v : reponseres) {
                                        if ((v.getType().equals("Trailer"))&& (v.getSite().equals("YouTube"))) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + v.getKey())));
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Videos> call, Throwable t) {
                                Toast.makeText(getBaseContext(),(String)"No trailers found for this movie", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
                average.setText(getResources().getText(R.string.averageDetail)+String.valueOf(result.getVoteAverage()));
                Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+result.getPosterPath()).into(posterImage);
                final String reviewsText ="";
                mService.getReviews(result.getId()).enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                        if(response.isSuccessful()){
                            List<com.example.hiren_pc_hp.popularmovies.network.json_schema.reviews.Result> items
                                    = response.body().getResults();
                            if(items.isEmpty()) {
                                reviews.append("No reviews for this movie");
                            } else {
                                for (com.example.hiren_pc_hp.popularmovies.network.json_schema.reviews.Result i : items) {
                                    reviews.append(getResources().getText(R.string.authorDetail) + i.getAuthor());
                                    reviews.append("\n");
                                    reviews.append(i.getContent());
                                    reviews.append("\n");
                                    reviews.append("\n");
                                }
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {

                    }
                });
            }
        });

    }

}
