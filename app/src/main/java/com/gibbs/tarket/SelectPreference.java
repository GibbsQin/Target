package com.gibbs.tarket;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SelectPreference extends Preference {
    private String mContent;
    private String mTitle;

    public SelectPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.pref_select);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.selectpref);
        mTitle = typedArray.getString(R.styleable.selectpref_title);
        Log.d("SelectPreference", "title = " + mTitle);
        typedArray.recycle();
    }

    public SelectPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectPreference(Context context) {
        this(context, null);
    }

    public void setContent(String content) {
        mContent = content;
        notifyChanged();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        //setWidgetLayoutResource(R.layout.select_preference);
        TextView textView = view.findViewById(R.id.selected);
        TextView title = view.findViewById(R.id.title);
        if (textView != null) {
            textView.setText(mContent);
        }
        if (title != null) {
            title.setText(mTitle);
        }
    }
}
