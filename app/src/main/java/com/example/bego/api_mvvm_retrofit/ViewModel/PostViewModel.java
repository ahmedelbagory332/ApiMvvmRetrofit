package com.example.bego.api_mvvm_retrofit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.bego.api_mvvm_retrofit.Models.FlagValue;
import com.example.bego.api_mvvm_retrofit.Models.PostModel;
import com.example.bego.api_mvvm_retrofit.NetWork.ApiClient;
import com.example.bego.api_mvvm_retrofit.RoomDB.MyDB;
import com.example.bego.api_mvvm_retrofit.RoomDB.Post;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends AndroidViewModel {


    public MutableLiveData<List<PostModel>> mutableLiveDataFRomServer = new MutableLiveData();
    public MutableLiveData<List<Post>> mutableLiveDataFRomCash = new MutableLiveData();


    public MutableLiveData<String> error = new MutableLiveData<>();


    public static MyDB myDB;


    public PostViewModel(@NonNull Application application) {
        super(application);
        myDB = Room.databaseBuilder(application, MyDB.class, "Post").allowMainThreadQueries().build();

    }


    public void getPostsFromServer() {


        ApiClient.getINSTANCE().getPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                myDB.postDao().restPostTable();
                List<PostModel> serverResponses = response.body();
                Post post = new Post();
                for (int i = 0; i < response.body().size(); i++) {
                    post.setName(serverResponses.get(i).getName());
                    post.setPost(serverResponses.get(i).getPost());
                    post.setTime(serverResponses.get(i).getTime());
                    post.setImgUrl(serverResponses.get(i).getImgUrl());
                    myDB.postDao().addPosts(post);
                }

                mutableLiveDataFRomServer.setValue(serverResponses);

            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {

                Log.d("bigo error  :  ", t.getMessage());
                error.setValue(t.getMessage());

            }
        });


    }

    public void getPostsFromCash() {

        List<Post> posts = myDB.postDao().getAllPosts();
        mutableLiveDataFRomCash.setValue(posts);


    }

}
