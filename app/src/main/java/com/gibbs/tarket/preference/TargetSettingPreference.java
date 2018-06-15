package com.gibbs.tarket.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gibbs.tarket.R;

public class TargetSettingPreference extends Preference {
    private int iconRes;
    private String title;

    public TargetSettingPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TargetSettingPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.pref_target_setting);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TargetSetting);
        iconRes = typedArray.getResourceId(R.styleable.TargetSetting_setting_icon,R.mipmap.add);
        title = typedArray.getString(R.styleable.TargetSetting_setting_title);
        typedArray.recycle();
    }

    public TargetSettingPreference(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TargetSettingPreference(Context context) {
        this(context,null);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View parentView = super.onCreateView(parent);
        if(null != parentView)
        {
            View listView = parentView.findViewById(android.R.id.list);
            if(null != listView)
            {
                listView.setPadding( 0, listView.getPaddingTop(), 0, listView.getPaddingBottom());
            }
        }
        return parentView;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        ImageView targetIcon = view.findViewById(R.id.target_setting_icon);
        TextView targetTitle = view.findViewById(R.id.target_setting_name);

        targetIcon.setBackgroundResource(iconRes);
        targetTitle.setText(title);
    }
}
