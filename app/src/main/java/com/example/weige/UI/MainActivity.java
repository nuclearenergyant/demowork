package com.example.weige.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;

import fragment.DemoFragment01;
import fragment.DemoFragment02;
import fragment.DemoFragment03;
import fragment.DemoFragment04;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String>mTitles;
    private List<Fragment>fragments;
    private FloatingActionButton floatingActionButton;//悬浮窗
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //除掉阴影
        getSupportActionBar().setElevation(0);
        initDate();
        initView();
        CrashReport.testJavaCrash();
    }

    private void initView() {
        tabLayout=findViewById( R.id.mTablayout);
        viewPager=findViewById(R.id.myviewpager);

        floatingActionButton=findViewById(R.id.fab_setting);
        //floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(this);

        viewPager.setOffscreenPageLimit(fragments.size());
        //viewpager的监听，实现在服务管家的界面实现FloatingActionButton消失
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int i) {
                Log.i("TAG","position"+i);
                 if (i==0) {
                     floatingActionButton.setVisibility(View.GONE);  //隐藏
                 } else
                     floatingActionButton.setVisibility(View.VISIBLE);  //显示
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        //TobLayout加入Viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initDate() {
        mTitles=new ArrayList<>();
        mTitles.add("服务管家");
        mTitles.add("微信竞选");
        mTitles.add("美女社区");
        mTitles.add("个人中心");

        fragments=new ArrayList<>();
        fragments.add(new DemoFragment01());
        fragments.add(new DemoFragment02());
        fragments.add(new DemoFragment03());
        fragments.add(new DemoFragment04());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }
}
