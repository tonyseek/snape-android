package com.tonyseek.snape.model;

import android.graphics.Bitmap;

public class ContactData {
    private int id;
    private String displayName;
    private Bitmap photo;

    public ContactData(int id, String displayName, Bitmap photo) {
        this.id = id;
        this.displayName = displayName;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ContactData) {
            return ((ContactData) other).id == id;
        }
        return super.equals(id);
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
