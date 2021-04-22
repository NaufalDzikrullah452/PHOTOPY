package com.example.photopy.lib;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;

import java.util.ArrayList;

public class viewModel extends ViewModel {

    private Repo repo;
    private MutableLiveData<ArrayList<ModelPost>> mutableLiveData;
    private MutableLiveData<ModelProfile> mutableLiveDataFirestore;
    private String message,messageStorage;


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


    public String addPhotoStorage(Uri uri,String authorIMG){
        if (messageStorage ==null){
            messageStorage = repo.addPhotoStorage(uri,authorIMG);
        }
        return messageStorage;
    }

}
