package com.example.lizetho.startupmaterialdesign.model;

import android.os.Bundle;

import com.example.lizetho.startupmaterialdesign.R;
import com.example.lizetho.startupmaterialdesign.common.activity.AbstractActivity;


public class HomeActivity extends AbstractActivity {

    @Override
    protected int getContentViewResourceId() {
        return R.layout.fragment_main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.home_page);
    }
}
