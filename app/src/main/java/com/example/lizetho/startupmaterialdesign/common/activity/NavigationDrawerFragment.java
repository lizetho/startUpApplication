package com.example.lizetho.startupmaterialdesign.common.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lizetho.startupmaterialdesign.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends Fragment {
    private static final String PREF_FILE_NAME = "PreferencesMD";
    private static final String KEY_USER_LEARNED_DRAWER = "KeyUserLearned";
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * This variable shows if the user has used at least once the application.
     */
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView actionListRV;
    private NavigationActionItemAdapter adapter;


    private static final String ARG_ITEM_NUMBER = "param1";

    private String mParam1;

    public void openDrawer(int start) {
        mDrawerLayout.openDrawer(start);
    }

    public void closeDrawer(int start) {
        mDrawerLayout.closeDrawer(start);
    }

    /**
     * Enum class for the list of items
     */
    public static enum NavigationItemEnum {
        /* Include all elements of the left menu*/
        HOME(R.string.home_page);
        int nameResource;

        /**
         * Constructor method for class NavigationItem.java
         */
        private NavigationItemEnum(int nameResource) {
            this.nameResource = nameResource;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NavegationDrawerFragment.
     */
    public static NavigationDrawerFragment newInstance(String param1) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //read from the preferences if the user has used before the app. If it's the first time, the navigation drawer will be open by default
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(this.getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        actionListRV = (RecyclerView) layout.findViewById(R.id.navigation_drawer_action_list);
        adapter = new NavigationActionItemAdapter(getActivity(), getData());
        actionListRV.setAdapter(adapter);
        actionListRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout layout, final Toolbar toolBar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), layout, toolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "" + mUserLearnedDrawer);
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawer) {
                super.onDrawerClosed(drawer);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawer, float slideOffset) {
                //To dark the toolbar when the drawer is opening
                super.onDrawerSlide(drawer, slideOffset);
                toolBar.setAlpha(1 - (slideOffset / 3));
            }

        };
        //The app will show the drawer just for the very first time the app is launched
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //This is to show the navigation drawer icon at top left corner (the 3 lines) :
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);

    }

    /**
     * get the elements to show in the navigation drawer
     *
     * @return list with picto and text title of the
     */
    public List<NavigationActionItem> getData() {
        List<NavigationActionItem> data = new ArrayList<>();
        NavigationItemEnum[] titles = NavigationItemEnum.values();
        for (int i = 0; i < titles.length; i++) {
            data.add(new NavigationActionItem(android.R.drawable.btn_star_big_off, getString(titles[i].nameResource)));
        }
        return data;
    }

    public void setSelectedItem(int position) {
        adapter.setSelectedItem(position);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }
}
