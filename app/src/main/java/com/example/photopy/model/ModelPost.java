package com.example.photopy.model;

import android.net.Uri;

public class ModelPost {
    public ModelPost() {

    }

    private String imageID;
    private String authorUID;
    private String authorNAME;
    private String authorIMG;
    private String imageURL;
    private int like;

    public ModelPost(String imageID, String authorUID, String authorNAME, String authorIMG, String imageURL, int like) {
        this.imageID = imageID;
        this.authorUID = authorUID;
        this.authorNAME = authorNAME;
        this.authorIMG = authorIMG;
        this.imageURL = imageURL;
        this.like = like;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
