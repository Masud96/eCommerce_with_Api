package com.masud.myproject;

public class UploadImageData {
    private String imageUrl, key;

    public UploadImageData() {
    }

    public UploadImageData(String imageUrl, String key) {
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
