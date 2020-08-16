package com.example.shoppingez;

public class ItemsModel {

    private String mName;
    private String mImageUri;

    public ItemsModel() {

    }

    public ItemsModel(String name, String imageUri) {

        if(name.trim().equals(""))
        {
            name="No Name";
        }

        this.mName = name;
        this.mImageUri = imageUri;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }
}
