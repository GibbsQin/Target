package com.gibbs.tarket.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.gibbs.tarket.R;


public class UserFragment extends PreferenceFragment {
    private static final String LOG_TAG = "UserFragment";

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_user);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = getView().findViewById(android.R.id.list);
        listView.setPadding(0,0,0,0);
    }
}
