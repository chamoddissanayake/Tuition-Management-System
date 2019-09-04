package com.test.tuitionmanagementsystem;

public class ModelVideo {

    private String image;
    private String title;
    private String link;
//    private String imagePreviewUrl;


    public ModelVideo(String image, String title, String link) {
        this.image = image;
        this.title = title;
        this.link = link;
//        this.imagePreviewUrl = imagePreviewUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public String getImagePreviewUrl() {
//        return imagePreviewUrl;
//    }

//    public void setImagePreviewUrl(String imagePreviewUrl) {
//        this.imagePreviewUrl = imagePreviewUrl;
//    }

}
