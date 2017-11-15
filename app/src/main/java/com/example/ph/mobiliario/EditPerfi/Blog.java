package com.example.ph.mobiliario.EditPerfi;

/**
 * Created by ph on 9/06/17.
 */

public class Blog {

    private String displayName;
    private String desc;
    private String image;

    public Blog()
    {

    }

    public Blog(String displayName, String desc, String image) {
        this.displayName = displayName;
        this.desc = desc;
        this.image = image;

    }

    public String getDisplayName() {



        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
