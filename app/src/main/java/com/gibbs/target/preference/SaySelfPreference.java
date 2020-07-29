package com.gibbs.target.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gibbs.target.R;

public class SaySelfPreference extends Preference {
    private static final String LOG_TAG = "SaySelfPreference";
    private EditText saySelf;
    private String content;

    public SaySelfPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SaySelfPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.pref_say_to_self);
    }

    public SaySelfPreference(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaySelfPreference(Context context) {
        this(context,null);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        saySelf = view.findViewById(R.id.say_self);
        saySelf.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(LOG_TAG, "onEditorAction " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    setContent(saySelf.getText().toString());
                    return true;
                }
                return false;
            }
        });
        saySelf.setText(content);
    }

    public String getSaySelf() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
