package com.prodalang1.models;

public class HomePost {
    private String productName;
    private String productImage;
    private float productPrice;
    private float productRating;

    public HomePost() {
        //empty constructor needed
    }

    public HomePost(String productName, float productPrice, float productRating, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productRating = productRating;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public float getProductRating() {
        return productRating;
    }

    public String getProductImage() {
        return productImage;
    }
}

