package com.example.photopy.model;

public class ModelProfile {

    private String AuthorUID;
    private String ImageAuthor;
    private String authorName;
    public ModelProfile(){}

    public ModelProfile(String authorUID, String imageAuthor, String authorName) {
        AuthorUID = authorUID;
        ImageAuthor = imageAuthor;
        this.authorName = authorName;
    }

    public String getAuthorUID() {
        return AuthorUID;
    }

    public void setAuthorUID(String authorUID) {
        AuthorUID = authorUID;
    }

    public String getImageAuthor() {
        return ImageAuthor;
    }

    public void setImageAuthor(String imageAuthor) {
        ImageAuthor = imageAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
