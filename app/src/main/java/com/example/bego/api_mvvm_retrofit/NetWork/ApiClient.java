package com.example.bego.api_mvvm_retrofit.NetWork;

import com.example.bego.api_mvvm_retrofit.Models.FlagResponse;
import com.example.bego.api_mvvm_retrofit.Models.FlagValue;
import com.example.bego.api_mvvm_retrofit.Models.PostModel;
import com.example.bego.api_mvvm_retrofit.Models.ServerResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.8/FaceBookPosts/php/";
    private ApiInterface apiInterface;
    private static ApiClient INSTANCE;

    public ApiClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public  Call<List<ServerResponse>> writePost(Map<String, RequestBody> user, MultipartBody.Part image){
        return apiInterface.storePost(user,image);
    }

    public  Call<List<FlagResponse>> setFlag(String flag){
        return apiInterface.setFlag(flag);
    }

    public  Call<List<PostModel>> getPosts(){
        return apiInterface.getPosts();
    }

    public  Call<FlagValue> getFlagValue(){
        return apiInterface.getFlag();
    }


}


