package com.theevilroot.theevilmanager.utils;

import android.support.annotation.DrawableRes;

import com.theevilroot.theevilmanager.R;

import java.util.List;

public class User {

    public int UID;
    public String name, surname, classification, picture, aliases;
    @DrawableRes
    public int classification_pic;

    public User(int UID, String name, String surname, String classification, String aliases, String picture) {
        this.UID = UID;
        this.name = name;
        this.surname = surname;
        this.classification = classification;
        this.aliases = aliases;
        this.classification_pic = getClassificationPic();
        this.picture = picture;
    }

    private int getClassificationPic() {
        switch (classification.toLowerCase()) {
            case "irrelevant": return R.drawable.irrelevant;
            case "asset": return R.drawable.asset;
            case "threat": return R.drawable.threat;
            case "admin": return R.drawable.asset;
            default:{
                if(classification.toLowerCase().contains("interface"))
                    return R.drawable.ainterface;
                return R.drawable.irrelevant;
            }
        }
    }
}
