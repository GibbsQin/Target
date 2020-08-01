package com.gibbs.target.ui;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.gibbs.target.R;


public class UserFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_user, rootKey);
    }
}
