package com.example.bego.api_mvvm_retrofit;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import android.net.Network;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bego.api_mvvm_retrofit.FileUrl.FileUtils;
import com.example.bego.api_mvvm_retrofit.GetTime.DwnloadTask;
import com.example.bego.api_mvvm_retrofit.Models.ServerResponse;
import com.example.bego.api_mvvm_retrofit.NetWork.ApiClient;
import com.example.bego.api_mvvm_retrofit.NetWork.CheckInternet;
import com.example.bego.api_mvvm_retrofit.RoomDB.MyDB;
import com.example.bego.api_mvvm_retrofit.ViewModel.PostViewModel;
import com.example.bego.api_mvvm_retrofit.ViewModel.uploadToServerViewModel;


import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.room.Room;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Button selectImg, uploadImg;
    EditText ed_name, ed_post;
    private static final int IMAGE = 100;
    Uri selectedImage;
    ProgressDialog pd;
    uploadToServerViewModel uploadToServerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.imageView);
        selectImg = (Button) findViewById(R.id.selectImg);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        ed_name = (EditText) findViewById(R.id._name);
        ed_post = (EditText) findViewById(R.id._post);
        pd = new ProgressDialog(this);

        uploadToServerViewModel = ViewModelProviders.of(this).get(uploadToServerViewModel.class);


        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isNetworkAvailable(getApplicationContext())) {
                    if (selectedImage == null || ed_name.getText().toString().isEmpty() || ed_post.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please check inputs", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.setMessage("uploading.....");
                        pd.show();
                        sendDataToViewModel();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Check Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });

        uploadToServerViewModel.imgUrl.observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(@Nullable Uri uri) {
                imageView.setImageURI(uri);

            }
        });


        uploadToServerViewModel.serverResponseMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

        uploadToServerViewModel.error.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                pd.dismiss();


            }
        });

    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the Image from data
            selectedImage = data.getData();
            uploadToServerViewModel.setUrl(selectedImage);
        }
    }

    void sendDataToViewModel() {

        uploadToServerViewModel.setAuthorName(ed_name.getText().toString());
        uploadToServerViewModel.setAuthorPost(ed_post.getText().toString());
        uploadToServerViewModel.uploadPost();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_posts:

                startActivity(new Intent(getApplicationContext(), Main2Activity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
