package com.example.dontworry.mall.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dontworry.mall.R;
import com.example.dontworry.mall.adapter.LocalVideoAdapter;
import com.example.dontworry.mall.base.BaseFragment;
import com.example.dontworry.mall.bean.MediaItem;

import java.util.ArrayList;

/**
 * Created by Don't worry on 2017/5/19.
 */

public class LocalVideoFragment extends BaseFragment {
    private TextView tv_nodata;
    private ListView lv;
    private ArrayList<MediaItem> mediaItems;
    private LocalVideoAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_local_video, null);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        lv = (ListView) view.findViewById(R.id.lv);
        return view;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems != null && mediaItems.size() > 0) {
                //有数据
                tv_nodata.setVisibility(View.GONE);
                //设置适配器
                adapter = new LocalVideoAdapter(context, mediaItems);
                lv.setAdapter(adapter);
            } else {
                //没有数据
                tv_nodata.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void initDate() {
        super.initDate();
        getData();
    }

    private void getData() {
        new Thread() {
            public void run() {
                mediaItems = new ArrayList<MediaItem>();
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

                        mediaItems.add(new MediaItem(name, duration, size, data));
                    }
                    cursor.close();
                }
            }
        }.start();
    }
}
