package com.example.bego.api_mvvm_retrofit.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bego.api_mvvm_retrofit.Models.PostModel;
import com.example.bego.api_mvvm_retrofit.R;
import com.example.bego.api_mvvm_retrofit.RoomDB.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapterOnLine extends RecyclerView.Adapter<PostAdapterOnLine.PostViewHolder> {
    private List<PostModel> postModelsOnline = new ArrayList<>();


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


        holder.name.setText(postModelsOnline.get(position).getName());
        holder.post.setText(postModelsOnline.get(position).getPost());
        holder.time.setText(postModelsOnline.get(position).getTime());
        Picasso.get().load(postModelsOnline.get(position).getImgUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return postModelsOnline.size();
    }

    public void setListOnline(List<PostModel> postList) {
        this.postModelsOnline = postList;
        notifyDataSetChanged();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView name, post, time;
        ImageView imageView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            post = itemView.findViewById(R.id.tv_post);
            time = itemView.findViewById(R.id.tv_time);
            imageView = itemView.findViewById(R.id.img_post);

        }
    }
}
