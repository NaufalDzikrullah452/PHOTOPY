package com.example.photopy.lib;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;

import java.util.ArrayList;

public class viewModel extends ViewModel {

    private final Repo repo;
    private MutableLiveData<ArrayList<ModelPost>> mutableLiveData;
    private MutableLiveData<ArrayList<ModelPost>> mutableLiveDataByUid;
    private MutableLiveData<ModelProfile> mutableLiveDataFirestore;
    private String messageStorage;


    public viewModel() {
        repo = new Repo();
    }
    public LiveData<ArrayList<ModelPost>> getDataPost() {

        if(mutableLiveData==null){
            mutableLiveData = repo.requestDataPost();
        }
        return mutableLiveData;
    }

    public LiveData<ModelProfile> getProfile() {

        if(mutableLiveDataFirestore==null){
            mutableLiveDataFirestore = repo.getAccountFirestore();
        }
        return mutableLiveDataFirestore;
    }


    public void addPhotoStorage(Uri uri,String authorIMG){
        repo.addPhotoStorage(uri,authorIMG);
    }

    public LiveData<ArrayList<ModelPost>> getDataByUid() {

        if(mutableLiveDataByUid==null){
            mutableLiveDataByUid = repo.getDataByUid();
        }
        return mutableLiveDataByUid;
    }

    public void downloadImage(String url, View view){
        repo.downloadImage(url,view);
    }

}
