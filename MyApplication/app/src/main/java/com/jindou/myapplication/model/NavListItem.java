package com.jindou.myapplication.model;

/**
 * Created by zhishi on 2017/2/14.
 */

public class NavListItem {
    private int navLeftDrawable;
    private String navItemName;
    private int navRightDrawable;

    public NavListItem() {
    }

    public NavListItem(int navLeftDrawable, String navItemName, int navRightDrawable) {
        this.navLeftDrawable = navLeftDrawable;
        this.navItemName = navItemName;
        this.navRightDrawable = navRightDrawable;
    }

    public int getNavLeftDrawable() {
        return navLeftDrawable;
    }

    public void setNavLeftDrawable(int navLeftDrawable) {
        this.navLeftDrawable = navLeftDrawable;
    }

    public String getNavItemName() {
        return navItemName;
    }

    public void setNavItemName(String navItemName) {
        this.navItemName = navItemName;
    }

    public int getNavRightDrawable() {
        return navRightDrawable;
    }

    public void setNavRightDrawable(int navRightDrawable) {
        this.navRightDrawable = navRightDrawable;
    }
}
