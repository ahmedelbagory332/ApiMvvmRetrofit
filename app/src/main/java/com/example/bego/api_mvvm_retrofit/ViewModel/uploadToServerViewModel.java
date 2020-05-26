package com.example.bego.api_mvvm_retrofit.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.bego.api_mvvm_retrofit.FileUrl.FileUtils;
import com.example.bego.api_mvvm_retrofit.GetTime.DwnloadTask;
import com.example.bego.api_mvvm_retrofit.Models.FlagResponse;
import com.example.bego.api_mvvm_retrofit.Models.ServerResponse;
import com.example.bego.api_mvvm_retrofit.NetWork.ApiClient;

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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class uploadToServerViewModel extends AndroidViewModel {


    public MutableLiveData<Uri> imgUrl = new MutableLiveData<>();
    public MutableLiveData<String> serverResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> authorNameMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> authorPostMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();


    private static Application context;

    public uploadToServerViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }


    public void setUrl(Uri url) {
        imgUrl.setValue(url);
    }

    public void setAuthorName(String name) {
        authorNameMutableLiveData.setValue(name);
    }

    public void setAuthorPost(String post) {
        authorPostMutableLiveData.setValue(post);
    }


    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = FileUtils.getFile(context, fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects
                                .requireNonNull(context.getContentResolver().getType(fileUri))),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public void uploadPost() {

        ApiClient.getINSTANCE().setFlag("true").enqueue(new Callback<List<FlagResponse>>() {
            @Override
            public void onResponse(Call<List<FlagResponse>> call, Response<List<FlagResponse>> response) {

                List<FlagResponse> flagResponses = response.body();
                String message = flagResponses.get(0).getMessage();

                if (message.equals("updated")) {
                    MultipartBody.Part fileToSend = prepareFilePart("image_path", imgUrl.getValue());
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), authorNameMutableLiveData.getValue());
                    RequestBody post = RequestBody.create(MediaType.parse("text/plain"), authorPostMutableLiveData.getValue());
                    RequestBody time = RequestBody.create(MediaType.parse("text/plain"), getTime());


                    Map<String, RequestBody> user = new HashMap<>();
                    user.put("name", name);
                    user.put("post", post);
                    user.put("time", time);


                    ApiClient.getINSTANCE().writePost(user, fileToSend).enqueue(new Callback<List<ServerResponse>>() {
                        @Override
                        public void onResponse(Call<List<ServerResponse>> call, Response<List<ServerResponse>> response) {

                            List<ServerResponse> serverResponses = response.body();

                            for (int i = 0; i < serverResponses.size(); i++) {

                                serverResponseMutableLiveData.setValue(serverResponses.get(i).getMessage());

                            }
                            ApiClient.getINSTANCE().setFlag("false").enqueue(new Callback<List<FlagResponse>>() {
                                @Override
                                public void onResponse(Call<List<FlagResponse>> call, Response<List<FlagResponse>> response) {

                                }

                                @Override
                                public void onFailure(Call<List<FlagResponse>> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<List<ServerResponse>> call, Throwable t) {

                            Log.d("bigo error  :  ", t.getMessage());
                            error.setValue(t.getMessage());
                        }
                    });
                } else {

                }


            }

            @Override
            public void onFailure(Call<List<FlagResponse>> call, Throwable t) {

            }
        });


    }

    //This method for get Date and Time from server
    public static String getTime() {

        String finalDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);//"2019-02-02T08:15+00:00"

        DwnloadTask task = new DwnloadTask();
        String result = null;
        try {
            result = task.execute("http://www.worldclockapi.com/api/json/gmt/now").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pattern pattern1 = Pattern.compile("\"currentDateTime\":(.*?),");
        Matcher matcher1 = pattern1.matcher(result);
        String full1 = null;
        while (matcher1.find()) {
            full1 = matcher1.group(1);
        }
        if (full1 != null) {
            full1 = full1.replace("\"", "");
            Date date = null;
            try {
                date = sdf.parse(full1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                finalDate = sdf1.format(date);
            }
        }
        return finalDate;
    }
}
