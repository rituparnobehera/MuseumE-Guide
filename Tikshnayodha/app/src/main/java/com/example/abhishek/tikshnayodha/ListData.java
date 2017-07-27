package com.example.abhishek.tikshnayodha;

import android.graphics.Bitmap;

/**
 * Created by ABHISHEK on 7/19/2017.
 */
public class ListData {
    String Description;
    String title;
    Bitmap imgResId;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImgResId() {
        return imgResId;
    }

    public void setImgResId(Bitmap imgResId) {
        this.imgResId = imgResId;
    }
}
