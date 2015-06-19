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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationMenuFragment extends Fragment {
    private static final String PREF_FILE_NAME = "PreferencesMD";
    private static final String KEY_USER_LEARNED_DRAWER = "KeyUserLearned";
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * This variable shows if the user has used at least once the application.
     */
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private DrawerLayout drawerLayout;
    private RecyclerView navigationMenuRV;
    private NavigationMenuAdapter navigationMenuAdapter;

    public void openDrawer(int start) {
        drawerLayout.openDrawer(start);
    }

    public void closeDrawer(int start) {
        drawerLayout.closeDrawer(start);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment NavegationDrawerFragment.
     */
    public static NavigationMenuFragment newInstance() {
        NavigationMenuFragment fragment = new NavigationMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationMenuFragment() {
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
        View layout = inflater.inflate(R.layout.fragment_navigation_menu, container, false);
        navigationMenuRV = (RecyclerView) layout.findViewById(R.id.navigation_drawer_action_list);
        navigationMenuAdapter = new NavigationMenuAdapter(getActivity(), NavigationMenuItemEnum.values());
        navigationMenuRV.setAdapter(navigationMenuAdapter);
        navigationMenuRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout layout, final Toolbar toolBar) {
        View containerView = getActivity().findViewById(fragmentId);
        drawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), layout, toolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
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
            saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "" + true);
            drawerLayout.openDrawer(containerView);
        }

        drawerLayout.setDrawerListener(mDrawerToggle);
        //This is to show the navigation drawer icon at top left corner (the 3 lines) :
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
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

    public void setSelectedItem(NavigationMenuItemEnum position) {
        navigationMenuAdapter.setSelectedItem(position);
    }

    public NavigationMenuItemEnum getSelectedItem() {
        return navigationMenuAdapter.getSelectedItem();
    }
}
