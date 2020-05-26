package com.example.bego.api_mvvm_retrofit;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bego.api_mvvm_retrofit.Adapter.PostAdapterOffLine;
import com.example.bego.api_mvvm_retrofit.Adapter.PostAdapterOnLine;
import com.example.bego.api_mvvm_retrofit.Models.FlagValue;
import com.example.bego.api_mvvm_retrofit.Models.PostModel;
import com.example.bego.api_mvvm_retrofit.NetWork.CheckInternet;
import com.example.bego.api_mvvm_retrofit.RoomDB.Post;
import com.example.bego.api_mvvm_retrofit.ViewModel.FlagViewModel;
import com.example.bego.api_mvvm_retrofit.ViewModel.PostViewModel;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ProgressDialog pd;
    PostViewModel postViewModel;
    FlagViewModel flagViewModel;
    boolean IsOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pd = new ProgressDialog(this);
        IsOnline = CheckInternet.isNetworkAvailable(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        flagViewModel = ViewModelProviders.of(this).get(FlagViewModel.class);


        pd.setMessage("uploading.......");
        pd.show();
        if (IsOnline) {

            flagViewModel.Flag();
            flagViewModel.mutableLiveDataFlag.observe(this, new Observer<FlagValue>() {
                @Override
                public void onChanged(@Nullable FlagValue flagValue) {

                    if (flagValue.getFlag().equals("true")) {

                        final PostAdapterOnLine postAdapterOnLine = new PostAdapterOnLine();
                        postViewModel.getPostsFromServer();
                        postViewModel.mutableLiveDataFRomServer.observe(Main2Activity.this, new Observer<List<PostModel>>() {
                            @Override
                            public void onChanged(@Nullable List<PostModel> postModels) {
                                postAdapterOnLine.setListOnline(postModels);
                                recyclerView.setAdapter(postAdapterOnLine);
                                pd.dismiss();

                            }
                        });
                    } else {
                        final PostAdapterOffLine postAdapterOffLine = new PostAdapterOffLine();
                        postViewModel.getPostsFromCash();
                        postViewModel.mutableLiveDataFRomCash.observe(Main2Activity.this, new Observer<List<Post>>() {
                            @Override
                            public void onChanged(@Nullable List<Post> posts) {
                                postAdapterOffLine.setListOffLine(posts);
                                recyclerView.setAdapter(postAdapterOffLine);
                                pd.dismiss();

                            }
                        });

                    }

                }
            });


        } else {

            final PostAdapterOffLine postAdapterOffLine = new PostAdapterOffLine();
            postViewModel.getPostsFromCash();
            postViewModel.mutableLiveDataFRomCash.observe(this, new Observer<List<Post>>() {
                @Override
                public void onChanged(@Nullable List<Post> posts) {
                    postAdapterOffLine.setListOffLine(posts);
                    recyclerView.setAdapter(postAdapterOffLine);
                    pd.dismiss();

                }
            });


        }

    }


}
