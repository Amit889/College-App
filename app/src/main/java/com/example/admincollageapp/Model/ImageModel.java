package com.example.admincollageapp.Model;

public class ImageModel {
    String downloadUrl, category;

    public ImageModel(String downloadUrl, String category) {
        this.downloadUrl = downloadUrl;
        this.category = category;
    }

    public ImageModel(){

    }
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
