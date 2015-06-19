package com.example.lizetho.startupmaterialdesign.activity.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lizetho.startupmaterialdesign.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeActivityFragment extends Fragment {
    public HomeActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
