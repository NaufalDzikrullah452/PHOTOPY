package com.example.photopy.model;

import android.net.Uri;

public class ModelPost {
    public ModelPost(){

    }


    private String authorUID;
    private String authorNAME;
    private String authorIMG;
    private String imageURL;
    private int like;

    public ModelPost(String authorUID, String authorNAME, String authorIMG, String imageURL, Integer like) {
        this.authorUID = authorUID;
        this.authorNAME = authorNAME;
        this.authorIMG = authorIMG;
        this.imageURL = imageURL;
        this.like = like;
    }

    public String getAuthorUID() {
        return authorUID;
    }

    public void setAuthorUID(String authorUID) {
        this.authorUID = authorUID;
    }

    public String getAuthorNAME() {
        return authorNAME;
    }

    public void setAuthorNAME(String authorNAME) {
        this.authorNAME = authorNAME;
    }

    public String getAuthorIMG() {
        return authorIMG;
    }

    public void setAuthorIMG(String authorIMG) {
        this.authorIMG = authorIMG;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
