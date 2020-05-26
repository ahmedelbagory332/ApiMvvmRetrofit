package com.example.bego.api_mvvm_retrofit.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bego.api_mvvm_retrofit.Models.FlagValue;
import com.example.bego.api_mvvm_retrofit.NetWork.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlagViewModel extends ViewModel {


    public MutableLiveData<FlagValue> mutableLiveDataFlag = new MutableLiveData<>();


    public void Flag() {

        ApiClient.getINSTANCE().getFlagValue().enqueue(new Callback<FlagValue>() {
            @Override
            public void onResponse(Call<FlagValue> call, Response<FlagValue> response) {

                mutableLiveDataFlag.setValue(response.body());

            }

            @Override
            public void onFailure(Call<FlagValue> call, Throwable t) {

            }
        });
    }

}
