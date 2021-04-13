package com.example.photopy.lib;

public class ModelProfile {

    private String AuthorUID;
    private String ImageAuthor;
    public ModelProfile(){}
    public ModelProfile(String authorUID, String imageAuthor) {
        AuthorUID = authorUID;
        ImageAuthor = imageAuthor;
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

}
