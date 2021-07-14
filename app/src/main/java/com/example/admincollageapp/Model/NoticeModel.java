package com.example.admincollageapp.Model;

public class NoticeModel {

    String title, pdfName, date, time,key,downloadUrl;

    public NoticeModel(String title, String downloadUrl, String date, String time, String key) {
        this.title = title;
        this.downloadUrl = downloadUrl;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public NoticeModel(String title,String pdfName, String donloadUrl) {
        this.title = title;
        this.downloadUrl = donloadUrl;
        this.pdfName=pdfName;
    }

    public NoticeModel() {
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
