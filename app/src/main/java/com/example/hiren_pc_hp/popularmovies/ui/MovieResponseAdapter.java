package com.example.hiren_pc_hp.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hiren_pc_hp.popularmovies.R;
import com.example.hiren_pc_hp.popularmovies.database.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieResponseAdapter extends RecyclerView.Adapter<MovieResponseAdapter.ViewHolder> {

    public interface OnItemClicked{
        void onItemClick(Result result);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgView;
        OnItemClicked mclickListener;

        public ViewHolder(View itemView, OnItemClicked onclicklistener) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.posterImage);
            this.mclickListener = onclicklistener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Result item = getItem(getAdapterPosition());
            this.mclickListener.onItemClick(item);
        }
    }
    private List<Result> mItems;
    private Context mContext;
    private OnItemClicked onclick;

    public MovieResponseAdapter(List<Result> mItems, Context mContext, OnItemClicked clicklistener) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.onclick = clicklistener;
    }
    public void update(List<Result> result){
        mItems = result;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int rvlayout = R.layout.main_movie_list;

        View v = inflater.inflate(rvlayout, parent, false);
        ViewHolder vh = new ViewHolder(v, this.onclick);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieResponseAdapter.ViewHolder holder, int position) {
        Result item = mItems.get(position);
        ImageView poster = holder.imgView;
        String posterpath = item.getPosterPath();
        String imgBaseURl = "http://image.tmdb.org/t/p/w500/";
        Picasso.with(poster.getContext()).load(
                imgBaseURl+posterpath).into(poster);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Result getItem(int adapterPos){
        return mItems.get(adapterPos);
    }
}
