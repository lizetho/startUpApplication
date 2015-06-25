package com.example.lizetho.startupmaterialdesign.common.activity;

import com.example.lizetho.startupmaterialdesign.R;

/**
 * Enum class for the list of items
 * Created by lizetho on 18/06/15.
 */
public enum NavigationMenuItemEnum {
    /* Include all elements of the left menu*/
    HOME(R.id.menu_item_1, R.drawable.ic_home_grey600_18dp, R.string.home_page), FAVORITES(R.id.menu_item_2, R.drawable.ic_favorite_grey600_18dp, R.string.favorites_page);
    int menuId;
    int iconResource;
    int nameResource;

    /**
     * Constructor method for class NavigationItem.java
     */
    NavigationMenuItemEnum(int menuId, int iconResource, int nameResource) {
        this.menuId = menuId;
        this.iconResource = iconResource;
        this.nameResource = nameResource;
    }

    public static NavigationMenuItemEnum getById(int id) {
        for (NavigationMenuItemEnum item : values()) {
            if (item.menuId == id)
                return item;
        }
        return null;
    }
}
