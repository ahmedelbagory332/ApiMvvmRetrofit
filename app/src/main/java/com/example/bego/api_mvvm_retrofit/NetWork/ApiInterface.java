package com.example.bego.api_mvvm_retrofit.NetWork;



import com.example.bego.api_mvvm_retrofit.Models.FlagResponse;
import com.example.bego.api_mvvm_retrofit.Models.FlagValue;
import com.example.bego.api_mvvm_retrofit.Models.PostModel;
import com.example.bego.api_mvvm_retrofit.Models.ServerResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {


    @Multipart
    @POST("uploadpost.php")
    Call<List<ServerResponse>> storePost(@PartMap Map<String, RequestBody> user, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("setFlag.php")
    public Call<List<FlagResponse>> setFlag(@Field("flag") String flag );

    @GET("getPosts.php")
    public Call<List<PostModel>> getPosts();

    @GET("getFlag.php")
    public Call<FlagValue> getFlag();
}
