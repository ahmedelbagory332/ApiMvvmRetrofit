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

public class PostAdapterOffLine extends RecyclerView.Adapter<PostAdapterOffLine.PostViewHolder> {
    private List<Post> postModelsOffline = new ArrayList<>();


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


        holder.name.setText(postModelsOffline.get(position).getName());
        holder.post.setText(postModelsOffline.get(position).getPost());
        holder.time.setText(postModelsOffline.get(position).getTime());
        Picasso.get().load(postModelsOffline.get(position).getImgUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {


        return postModelsOffline.size();
    }


    public void setListOffLine(List<Post> postList) {
        this.postModelsOffline = postList;
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
