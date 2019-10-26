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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tencent.bugly.crashreport.CrashReport;

import adapter.CardviewForbillAdapter;
import entity.Bill;
import fragment.DemoFragment01;
import fragment.DemoFragment02;
import fragment.DemoFragment03;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    /*有用的*/
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

        /*有用*/
        tabLayout=findViewById( R.id.mTablayout);
        viewPager=findViewById(R.id.myviewpager);

        //浮窗
        floatingActionButton=findViewById(R.id.fab_setting);

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

        //设置viewpaper滑动刷新
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


        viewPager.setCurrentItem(1);//括号里的x变成你的默认页码
        //TobLayout加入Viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initDate() {
        mTitles=new ArrayList<>();
        mTitles.add("分类");
        mTitles.add("个人");
        mTitles.add("订单");

        fragments=new ArrayList<>();
        fragments.add(new DemoFragment01());
        fragments.add(new DemoFragment02());
        fragments.add(new DemoFragment03());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu,menu );

        return true;
    }

    //标题栏上的快捷键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(MainActivity.this,R.string.item_id_in,Toast.LENGTH_SHORT).show();
                Intent intent =new Intent( MainActivity.this,LoginActivity.class );
                startActivity( intent );
                finish();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this,R.string.item_id_out,Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
        return true;
    }
}
