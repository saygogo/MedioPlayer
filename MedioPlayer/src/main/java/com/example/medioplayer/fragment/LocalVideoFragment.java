package com.example.medioplayer.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medioplayer.R;
import com.example.medioplayer.acticity.SystemVideoPlayerActivity;
import com.example.medioplayer.adapter.LocalVideoAdapter;
import com.example.medioplayer.base.BaseFragment;
import com.example.medioplayer.bean.Bean;

import java.util.ArrayList;

/**
 * Created by Don't worry on 2017/5/19.
 */

public class LocalVideoFragment extends BaseFragment {
    private ListView lv;
    private TextView tv_nodata;
    private LocalVideoAdapter adapter;
    private ArrayList<Bean> bean;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_local_video, null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);

                Bundle bunlder = new Bundle();
                bunlder.putSerializable("videolist",bean);
                intent.putExtra("position",i);
                //放入Bundler
                intent.putExtras(bunlder);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        getDate();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (bean.size() > 0 && bean != null) {
                tv_nodata.setVisibility(View.GONE);
                adapter = new LocalVideoAdapter(context, bean);
                lv.setAdapter(adapter);
            } else {
                tv_nodata.setVisibility(View.VISIBLE);
            }
        }
    };

    public void getDate() {
        new Thread() {
            public void run() {
                bean = new ArrayList<Bean>();
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频在sdcard上的名称
                        MediaStore.Video.Media.DURATION,//视频时长
                        MediaStore.Video.Media.SIZE,//视频文件的大小
                        MediaStore.Video.Media.DATA//视频播放地址
                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        Log.e("TAG", "name==" + name + ",duration==" + duration + ",data===" + data);

                        bean.add(new Bean(name, duration, size, data));

                        //使用handler
                        handler.sendEmptyMessage(0);
                    }

                    cursor.close();
                }
            }
        }.start();
    }
}
