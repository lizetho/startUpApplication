package com.example.lizetho.startupmaterialdesign.activity.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lizetho.startupmaterialdesign.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoritesActivityFragment extends Fragment {

    @InjectView(R.id.favorites_text)
    TextView textView;

    public FavoritesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.inject(this, root);
        textView.setText(
                getActivity().getResources().getString(R.string.title_activity_favorites));
        return root;
    }
}
