package com.prodalang1.models;

public class UploadModel {

    // Variable Intialization
    private String uploadProductName; //Upload product name
    private float uploadProductPrice; // Upload product price
    private float uploadProductRating; // Upload product rating
    private String uploadProductImage; // Upload product image

    //private String mKey;

    // Creating empty constructor required
    public UploadModel() {}

    // Creating class constructor
    public UploadModel (String name, float price, float rating, String image/*, String key*/){
        this.uploadProductName = name;
        this.uploadProductPrice = price;
        this.uploadProductImage = image;
        this.uploadProductRating = rating;
    }


    // Set getters
    public String getUploadProductName() {
        return uploadProductName;
    }

    public float getUploadProductPrice() {
        return uploadProductPrice;
    }

    public float getUploadProductRating() {
        return uploadProductRating;
    }

    public String getUploadProductImage() {
        return uploadProductImage;
    }

    /*public String getmKey() {
        return mKey;
    }*/


    // Set setters
    public void setUploadProductName(String uploadProductName) {
        this.uploadProductName = uploadProductName;
    }

    public void setUploadProductPrice(float uploadProductPrice) {
        this.uploadProductPrice = uploadProductPrice;
    }

    public void setUploadProductRating(float uploadProductRating) {
        this.uploadProductRating = uploadProductRating;
    }

    public void setUploadProductImage(String uploadProductImage) {
        this.uploadProductImage = uploadProductImage;
    }

    /*public void setmKey(String mKey) {
        this.mKey = mKey;
    }*/
}
