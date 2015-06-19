package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lizetho.startupmaterialdesign.R;
import com.example.lizetho.startupmaterialdesign.activity.favorite.FavoritesActivity;
import com.example.lizetho.startupmaterialdesign.activity.home.HomeActivity;


/**
 * Abstract Activity for all activities using a navigation drawer.
 * Created by lizetho on 16/06/15.
 */
public abstract class AbstractActivity extends AppCompatActivity {

    public static final String SELECTED_NAVIGATION_MENU_ITEM = "selectedNavigationMenuItem";
    private final String TAG = "AbstractActivity";

    //Tool bar
    private Toolbar toolbar;

    //Navigation Drawer
    private NavigationMenuFragment navigationMenuFragment;

    //===================== OVERRIDE METHODS ======================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);

        //Only portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initContainView();

        initHeader();

        initNavigationMenu();

    }

    /**
     * Initialize the containing of the activity by replacing the container layout for the activity layout xml
     */
    private void initContainView() {
        // Get a reference to the score_name_entry object in score.xml
        RelativeLayout containingLayout = (RelativeLayout) findViewById(R.id.activity_layout_container);
        ViewGroup.LayoutParams layoutParams = containingLayout.getLayoutParams();
        containingLayout.removeAllViews();

        //Set the view
        if (getContentViewResourceId() != 0) {
            // Create new LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            containingLayout.addView(inflater.inflate(getResources().getLayout(getContentViewResourceId()), null), layoutParams);
        } else {
            Log.e(TAG, "Activity cannot be created because no resource view was found. Please check you have implemented method getContentViewResourceId().");
            finish();
        }

    }

    /**
     * Checks the selected item and starts the new activity if it is not the same than the current selected activity
     *
     * @param itemSelected new selected item
     */
    public void onNavigationMenuItemClicked(NavigationMenuItemEnum itemSelected) {
        if (itemSelected != null) {
            closeMenu();
            Intent intent = null;
            switch (itemSelected) {
                case HOME:
                    intent = new Intent(this, HomeActivity.class);
                    break;
                case FAVORITES:
                    intent = new Intent(this, FavoritesActivity.class);
                    break;
            }
            if (intent != null && navigationMenuFragment.getSelectedItem() != itemSelected) {
                intent.putExtra(SELECTED_NAVIGATION_MENU_ITEM, itemSelected.name());
                startActivity(intent);
            } else {
                Log.e(TAG, "The selected item is the current selected item or it has not a related Activity");
            }
        }
    }

    //=========== CONFIGURE HEADER =================

    /**
     * Initialization header. Put the title
     */
    private void initHeader() {
        //Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        String itemName = getIntent().getStringExtra(SELECTED_NAVIGATION_MENU_ITEM);
        if (itemName != null) {
            setTitle(NavigationMenuItemEnum.valueOf(itemName).nameResource);
        }
    }

    /**
     * Open Navigation drawer
     */
    private void openMenu() {
        Log.d(TAG, "openMenu");
        if (navigationMenuFragment != null) {
            navigationMenuFragment.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Close navigation drawer
     */
    private void closeMenu() {
        Log.d(TAG, "closeMenu");
        if (navigationMenuFragment != null) {
            navigationMenuFragment.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Change header title.
     *
     * @param idTitle
     */
    public void headerSetTitle(int idTitle) {
        if (toolbar != null) {
            toolbar.setTitle(idTitle);
        }
    }

    //===================== PUBLIC METHODS ======================

    /**
     * Initialize the navigation menu. Put the selected item.
     */
    private void initNavigationMenu() {
        navigationMenuFragment = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        if (navigationMenuFragment != null) {
            navigationMenuFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
            String itemName = getIntent().getStringExtra(SELECTED_NAVIGATION_MENU_ITEM);
            if (itemName != null) {
                navigationMenuFragment.setSelectedItem(NavigationMenuItemEnum.valueOf(itemName));
            } else {
                navigationMenuFragment.setSelectedItem(NavigationMenuItemEnum.HOME);
            }
        }
    }
    //===================== ABSTRACT METHODS ======================

    /**
     * The activity layout to show below the toolbar
     *
     * @return the resource layout. For example R.layout.activity_main;
     */
    protected abstract int getContentViewResourceId();

}
