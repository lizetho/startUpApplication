package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lizetho.startupmaterialdesign.R;


/**
 * Abstract Activity for all activities using a navigation drawer.
 * Created by lizetho on 16/06/15.
 */
public abstract class AbstractActivity extends AppCompatActivity {

    private final String TAG = "AbstractActivity";

    //Tool bar
    private Toolbar toolbar;

    //Navigation Drawer
    private NavigationDrawerFragment navigationMenuFragment;

    //Flag to know if the menu is opened or not.
    private boolean isDrawerOpened = false;

    //Flag to know if header menu button is enable
    private boolean isMenuEnable = true;


    //===================== ABSTRACT METHODS ======================

    /**
     * The activity layout to show below the toolbar
     *
     * @return the resource layout. For example R.layout.activity_main;
     */
    protected abstract int getContentViewResourceId();

    //===================== OVERRIDE METHODS ======================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        // Get a reference to the score_name_entry object in score.xml
        RelativeLayout containingLayout = (RelativeLayout) findViewById(R.id.fragment_container);
        ViewGroup.LayoutParams layoutParams = containingLayout.getLayoutParams();
        containingLayout.removeAllViews();

        //Set the view
        if (getContentViewResourceId() != 0) {
            // Create new LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            containingLayout.addView(inflater.inflate(getResources().getLayout(getContentViewResourceId()), null), layoutParams);
        } else {
            Log.e(TAG, "Activity cannot be created because no resource view was found. Please check you have implemented method getContentViewResourceId().");
            return;
        }

        //Only portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initialisation of the menu.
        if (hasMenu()) {
            Log.d(TAG, "onCreate: initialization of menu");
            initMenu();
        } else {
            Log.d(TAG, "onCreate: no menu");
        }

        /*//Define the item menu selected.
        if (navigationMenuAdapter != null) {
            navigationMenuAdapter.selectMenuItem(getSelectedMenuItem());
        }*/

        //Initialization header
        initHeader();

    }

    public void onMenuItemClicked(NavigationDrawerFragment.NavigationItemEnum itemSelected) {
        if (itemSelected != null) {
            closeMenu();
            if (itemSelected == NavigationDrawerFragment.NavigationItemEnum.HOME) {
                //TODO startActivity(new Intent(this, HomepageActivity.class));
            }
        }
    }

    //=========== CONFIGURE HEADER =================

    /**
     * Initialization header.
     */
    private void initHeader() {
        //Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                if (navigationMenuFragment != null) {
                    if (navigationMenuFragment.getDrawerLayout().getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_UNLOCKED) {
                        if (isMenuEnable) {
                            if (isDrawerOpened) {
                                closeMenu();
                            } else {
                                openMenu();
                            }
                        } else {
                            Log.d(TAG, "onOptionsItemSelected: click on back");
                            //onHeaderBackButton();
                        }
                    }
                } else {
                    Log.d(TAG, "onOptionsItemSelected: click on back");
                    //onHeaderBackButton();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Open Navigation drawer
     */
    public void openMenu() {
        Log.d(TAG, "openMenu");
        if (navigationMenuFragment != null) {
            navigationMenuFragment.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Close navigation drawer
     */
    public void closeMenu() {
        Log.d(TAG, "closeMenu");
        if (navigationMenuFragment != null) {
            navigationMenuFragment.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Enable or disable the header back button
     * <p/>
     * public void headerEnableBack(boolean enable) {
     * if (getSupportActionBar() != null) {
     * getSupportActionBar().setHomeAsUpIndicator(R.drawable.ico_backbtn);
     * getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
     * }
     * }
     * <p/>
     * /**
     * Enable or disable the header menu button
     *
     * @param enable
     */
    public void headerEnableMenu(boolean enable) {
        if (enable && navigationMenuFragment != null && toolbar != null) {
            isMenuEnable = enable;
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    navigationMenuFragment.getDrawerLayout(),
                    toolbar,
                    R.string.open,
                    R.string.close) {
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    isDrawerOpened = false;
                    invalidateOptionsMenu();
                    syncState();
                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    isDrawerOpened = true;
                    invalidateOptionsMenu();
                    syncState();
                }
            };
            navigationMenuFragment.getDrawerLayout().setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
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

    //===================== PROTECTED METHODS ======================

    /**
     * Returned if the screen has a menu or not.
     *
     * @return true if there is a menu.
     */
    protected boolean hasMenu() {
        return findViewById(R.id.drawer_layout) != null;
    }

    //===================== PUBLIC METHODS ======================

    /**
     * Inits the menu.
     */
    public void initMenu() {
        navigationMenuFragment = NavigationDrawerFragment.newInstance(null);
    }

}
