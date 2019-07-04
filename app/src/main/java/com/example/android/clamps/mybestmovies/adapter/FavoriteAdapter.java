package com.example.android.clamps.mybestmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.clamps.mybestmovies.R;
import com.example.android.clamps.mybestmovies.REtrofit.OnFavoriteClickCallBack;
import com.example.android.clamps.mybestmovies.room.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    List<Favorite>favorites;
    private OnFavoriteClickCallBack callback;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public FavoriteAdapter(List<Favorite>favorites,OnFavoriteClickCallBack callback){
        this.favorites=favorites;
        this.callback=callback;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie,viewGroup, false);
        return new  FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder, int i) {
          favoriteHolder.bind(favorites.get(i));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }
    public void setItemCount(List<Favorite>favorites){
        this.favorites=favorites;
        notifyDataSetChanged();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder{
        Favorite favorite;
        ImageView im;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            im=itemView.findViewById(R.id.item_movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(favorite);
                }
            });

        }
        public void bind(Favorite favorite){
            this.favorite=favorite;
            String s=IMAGE_BASE_URL+favorite.getPoster_path();
            Picasso.get().load(s).into(im);

        }
    }
}
