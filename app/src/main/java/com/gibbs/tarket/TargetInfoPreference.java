package com.gibbs.tarket;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class TargetInfoPreference extends Preference {
    private static final String LOG_TAG = "TargetInfoPreference";
    private EditText editText;
    private String name;

    public TargetInfoPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TargetInfoPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.pref_icon);
    }

    public TargetInfoPreference(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TargetInfoPreference(Context context) {
        this(context,null);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        Log.d(LOG_TAG, "onBindView " + name);
        editText = view.findViewById(R.id.name);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(LOG_TAG, "onEditorAction " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setEditText(editText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        editText.setText(name);
    }

    public String getEditText() {
        if (editText != null) {
            return editText.getText().toString();
        }

        return null;
    }

    public void setEditText(String name) {
        this.name = name;
    }
}
