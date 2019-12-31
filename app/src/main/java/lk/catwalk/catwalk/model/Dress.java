package lk.catwalk.catwalk.model;

import android.annotation.SuppressLint;

import java.util.Objects;

public class Dress {
    private String title;
    private double price;
    private String Shop;
    private String url;
    private String image;
    private boolean isLike;
    private String uid;

    public Dress(String url) {
        this.url = url;
    }

    public Dress(String title, double price, String shop, String url, String image) {
        this.title = title;
        this.price = price;
        Shop = shop;
        this.url = url;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getShop() {
        return Shop;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dress dress = (Dress) o;
        return Objects.equals(url, dress.url);
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
