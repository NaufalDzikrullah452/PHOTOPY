package com.example.photopy.model;

public class ModelLike {
    public ModelLike() {
    }
    private String likeID;
    private String likenAuthorUid;

    public ModelLike(String likeID, String likenAuthorUid) {
        this.likeID = likeID;
        this.likenAuthorUid = likenAuthorUid;
    }

    public String getLikeID() {
        return likeID;
    }

    public void setLikeID(String likeID) {
        this.likeID = likeID;
    }

    public String getLikenAuthorUid() {
        return likenAuthorUid;
    }

    public void setLikenAuthorUid(String likenAuthorUid) {
        this.likenAuthorUid = likenAuthorUid;
    }
}
