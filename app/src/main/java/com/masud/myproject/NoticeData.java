package com.masud.myproject;

public class NoticeData {

    String postTitle,postDate,postTime,postKey,postImage;

    public NoticeData() {



    }


    public NoticeData(String postTitle, String postDate, String postTime, String postKey, String postImage) {
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postTime = postTime;
        this.postKey = postKey;
        this.postImage = postImage;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }


}
