package com.example.photopy.lib;

import android.net.Uri;

public class ModelPost {
    public ModelPost(){

    }


    private String authorUID;
    private String imageURL;
    private String imageCaption;
    private String like;

    public ModelPost(String authorUID, String imageURL, String imageCaption, String like) {
        this.authorUID = authorUID;
        this.imageURL = imageURL;
        this.imageCaption = imageCaption;
        this.like = like;
    }

    public String getAuthorUID() {
        return authorUID;
    }

    public void setAuthorUID(String authorUID) {
        this.authorUID = authorUID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
