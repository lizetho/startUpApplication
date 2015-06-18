package com.example.lizetho.startupmaterialdesign.common.activity;

/**
 * Created by lizetho on 10/04/15.
 */
public class NavigationActionItem {
    private int iconId;
    private String title;

    public NavigationActionItem(int icon, String title) {
        this.iconId=icon;
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public String getTitle() {
        return title;
    }

}
