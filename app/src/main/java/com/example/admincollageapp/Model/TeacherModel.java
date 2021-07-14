package com.example.admincollageapp.Model;

public class TeacherModel {
    String name , email, post , branch, downloadUrl,uniqueKey;

    public TeacherModel(String name, String email, String post, String branch, String downloadUrl,String uniqueKey) {
        this.name = name;
        this.email = email;
        this.post = post;
        this.branch = branch;
        this.downloadUrl = downloadUrl;
        this.uniqueKey=uniqueKey;

    }


    public TeacherModel() {
    }


    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
