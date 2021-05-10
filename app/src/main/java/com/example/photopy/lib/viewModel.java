package com.example.photopy.lib;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.photopy.model.ModelCollection;
import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;

import java.util.ArrayList;

public class viewModel extends ViewModel {

    private final Repo repo;
    private MutableLiveData<ArrayList<ModelPost>> mutableLiveData;
    private MutableLiveData<ArrayList<ModelPost>> mutableLiveDataByUid;
    private MutableLiveData<ModelProfile> mutableLiveDataFirestore;
    private MutableLiveData<ArrayList<ModelCollection>> mutableLiveDataCollection;

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
    public void setProfile(String name) {
       repo.uploadName(name);
        }
    public void setImage(String name) {
        repo.uploadimage(name);
    }

    public void addPhotoStorage(Uri uri, String authorIMG, Context context){
        repo.addPhotoStorage(uri,authorIMG,context);
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

    public void addCollection(String url,String imageID){
        repo.addCollection(url,imageID);
    }
    public void addLike(String imageID,String AuthorID){
        repo.addlike(imageID,AuthorID);
    }
    public LiveData<ArrayList<ModelCollection>> getDataCollectionByID() {

        if(mutableLiveDataCollection==null){
            mutableLiveDataCollection = repo.getDataCollection();
        }
        return mutableLiveDataCollection;
    }

}
