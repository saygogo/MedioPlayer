package com.example.medioplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medioplayer.R;
import com.example.medioplayer.bean.Bean;
import com.example.medioplayer.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Don't worry on 2017/5/19.
 */

public class LocalVideoAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Bean> bean;
    private Utils utils;

    public LocalVideoAdapter(Context context, ArrayList<Bean> bean) {
        this.context = context;
        this.bean = bean;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return bean == null ? 0 : bean.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_local_video, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        Bean bean = this.bean.get(position);
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context, bean.getSize()));
        viewHolder.tv_duration.setText(utils.stringForTime((int) bean.getDuration()));


        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_duration;
        TextView tv_size;
    }
}
