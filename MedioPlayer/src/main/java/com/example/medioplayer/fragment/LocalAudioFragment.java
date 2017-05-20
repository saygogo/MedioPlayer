package com.example.medioplayer.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.medioplayer.base.BaseFragment;

/**
 * Created by Don't worry on 2017/5/19.
 */

public class LocalAudioFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initDate() {
        super.initDate();
        textView.setText("本地音乐");
    }
}
