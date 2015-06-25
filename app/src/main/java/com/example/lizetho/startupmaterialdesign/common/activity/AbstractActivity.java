package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lizetho.startupmaterialdesign.R;
import com.example.lizetho.startupmaterialdesign.activity.favorite.FavoritesActivity;
import com.example.lizetho.startupmaterialdesign.activity.home.HomeActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Abstract Activity for all activities using a navigation drawer.
 * Created by lizetho on 16/06/15.
 */
public abstract class AbstractActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREF_FILE_NAME = "PreferencesMD";
    private static final String KEY_USER_LEARNED_DRAWER = "KeyUserLearned";
    private final String TAG = "AbstractActivity";

    //Tool bar
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.navigation_drawer)
    NavigationView navigationView;

    //Navigation Drawer
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private final Handler mDrawerActionHandler = new Handler();
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private boolean mUserLearnedDrawer;

    //===================== OVERRIDE METHODS ======================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        ButterKnife.inject(this);

        //Only portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initContainView();

        initHeader();

        initNavigationMenu(savedInstanceState);

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
     * @param id new selected item
     */
    public void navigate(int id) {
        NavigationMenuItemEnum itemSelected = NavigationMenuItemEnum.getById(id);
        if (itemSelected != null) {
            Intent intent = null;
            switch (itemSelected) {
                case HOME:
                    intent = new Intent(this, HomeActivity.class);
                    break;
                case FAVORITES:
                    intent = new Intent(this, FavoritesActivity.class);
                    break;
            }
            if (intent != null && mNavItemId != id) {
                mNavItemId = id;
                intent.putExtra(NAV_ITEM_ID, id);
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
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        int menuItemId = getIntent().getIntExtra(NAV_ITEM_ID, -1);
        if (menuItemId != -1) {
            NavigationMenuItemEnum menuItemEnum = NavigationMenuItemEnum.getById(menuItemId);
            if (menuItemEnum != null) {
                setTitle(menuItemEnum.nameResource);
            }
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
    private void initNavigationMenu(Bundle savedInstanceState) {
        // load saved navigation state if present
        if (null == savedInstanceState) {
            //value it's coming from a new activity
            mNavItemId = getIntent().getIntExtra(NAV_ITEM_ID, R.id.menu_item_1);
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // listen for navigation events
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //read from the preferences if the user has used before the app. If it's the first time, the navigation drawer will be open by default
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(this, KEY_USER_LEARNED_DRAWER, Boolean.toString(false)));

        //The app will show the drawer just for the very first time the app is launched
        if (!mUserLearnedDrawer) {
            saveToPreferences(this, KEY_USER_LEARNED_DRAWER, Boolean.toString(true));
            mDrawerLayout.openDrawer(navigationView);
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);

        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    private static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    private static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);

    }

    //===================== ABSTRACT METHODS ======================

    /**
     * The activity layout to show below the toolbar
     *
     * @return the resource layout. For example R.layout.activity_main;
     */
    protected abstract int getContentViewResourceId();

}
