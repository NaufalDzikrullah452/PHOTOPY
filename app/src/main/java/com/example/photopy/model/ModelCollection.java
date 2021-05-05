package com.example.photopy.model;

public class ModelCollection {
    public ModelCollection() {
    }
    private String collectionID;
    private String collectionAuthorUid;
    private String imageUrl;

    public ModelCollection(String collectionID, String collectionAuthorUid, String imageUrl) {
        this.collectionID = collectionID;
        this.collectionAuthorUid = collectionAuthorUid;
        this.imageUrl = imageUrl;
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getCollectionAuthorUid() {
        return collectionAuthorUid;
    }

    public void setCollectionAuthorUid(String collectionAuthorUid) {
        this.collectionAuthorUid = collectionAuthorUid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
