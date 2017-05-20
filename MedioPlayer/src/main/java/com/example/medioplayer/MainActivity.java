package com.example.medioplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.medioplayer.base.BaseFragment;
import com.example.medioplayer.fragment.LocalAudioFragment;
import com.example.medioplayer.fragment.LocalVideoFragment;
import com.example.medioplayer.fragment.NetAudioFragment;
import com.example.medioplayer.fragment.NetVideoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        initFragment();
        rg_main.setOnCheckedChangeListener(new MyOnClick());
        rg_main.check(R.id.rb_local_video);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new NetVideoFragment());
    }

    private class MyOnClick implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rb_local_video:
                    position = 0;
                    break;
                case R.id.rb_local_audio:

                    position = 1;
                    break;
                case R.id.rb_net_audio:

                    position = 2;
                    break;
                case R.id.rb_net_video:

                    position = 3;
                    break;
            }
            BaseFragment currentFragmrnt = fragments.get(position);
            addFragment(currentFragmrnt);
        }
    }

    private void addFragment(Fragment currentFragmrnt) {
        if (tempFragment != currentFragmrnt) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!currentFragmrnt.isAdded()) {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.add(R.id.fl_content, currentFragmrnt);
            } else {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.show(currentFragmrnt);
            }
            ft.commit();
            tempFragment = currentFragmrnt;
        }
    }
}
