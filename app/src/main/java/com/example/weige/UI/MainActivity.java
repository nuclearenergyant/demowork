package com.example.weige.UI;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.navigation.ui.AppBarConfiguration;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.tabs.TabLayout;

import entity.Garbage;
import fragment.DemoFragment01;
import fragment.DemoFragment02;
import fragment.DemoFragment03;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //搜索栏的返回list
    private List<Garbage>searchlist=new ArrayList<>();
    //搜索控件
    androidx.appcompat.widget.SearchView searchView;

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawerLayout;
    /*有用的*/
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String>mTitles;
    private List<Fragment>fragments;
    private FloatingActionButton floatingActionButton;//悬浮窗
    private Button button_to_add_bill;

    //内部类,给设置
    class ButtomListener implements Animator.AnimatorListener {
        @SuppressLint("RestrictedApi")
        @Override
        public void onAnimationStart(Animator animation) {

        }
        @SuppressLint("RestrictedApi")
        @Override
        public void onAnimationEnd(Animator animation) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( "丐帮" );


        //打印全局变量user_id
        L.i( "用户的编号位为:  "+ APITOALIYUN.id  );
        //除掉阴影
        getSupportActionBar().setElevation(0);
        initDate();
        initView();

    }

    private void initView() {

        //下单按钮
        button_to_add_bill=findViewById( R.id.add_bill );
        button_to_add_bill.setOnClickListener( this );
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
                 if (i==0||i==2) {
                     button_to_add_bill.setVisibility( View.GONE );
                     floatingActionButton.setVisibility(View.GONE);  //隐藏
                 } else{
                     floatingActionButton.setVisibility(View.VISIBLE);  //显示
                     button_to_add_bill.setVisibility( View.VISIBLE );
                 }
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
               // startActivity(new Intent(this,SettingActivity.class));
                //ValueAnimator anim = ObjectAnimator.ofFloat(v, "translationY", 0, v.getHeight()*2);
                ValueAnimator anim = ObjectAnimator.ofFloat(v, "translationY", 0, 0);
                anim.setDuration(200);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.addListener(new ButtomListener());
                anim.start();
                break;
            case R.id.add_bill:
                startActivity( new Intent( this,ScrollingActivity.class ) );
                finish();
                break;
        }
    }

    //设置搜索框
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate( R.menu.menu,menu );
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(menuItem);

        //设置搜索的事件
        searchView.setOnQueryTextListener( new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                OkHttpClient okHttpClient_garbage=new OkHttpClient();
                Request.Builder builder=new Request.Builder();
                Request request=builder.get().url( APITOALIYUN.Garbage_API).method( "GET",null ).build();
                okhttp3.Call call=okHttpClient_garbage.newCall(request);
                try {
                    Response response=call.execute();
                    final String str_garbage = response.body().string();
                    L.i( str_garbage );
                    JSONObject jsonObject=JSONObject.parseObject( str_garbage );
                    L.i(  jsonObject.getString( "result"));
                    JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString( "result" ));
                    JSONObject row = null;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        row = jsonArray.getJSONObject(i);
                        Garbage garbage=new Garbage( row.getString( "name" ),row.getInteger( "category" ) );
                        searchlist.add( garbage );
                    }
                    L.i( "开始搜索" );
                    for (int j=0;j<searchlist.size();j++){
                        Garbage garbage=searchlist.get( j );
                        //L.i( garbage.getName() );
                        if (garbage.getName().equals( "安全套") ){
                            L.i( "安全套存在" );
                        }
//                        if (garbage.getName().equals( query )){
//                            Toast t;
//                            switch (garbage.getCategory()){
//                                case 1:
//                                    t=Toast.makeText( MainActivity.this, query+"是可回收垃圾" ,Toast.LENGTH_LONG);
//                                    t.setGravity( Gravity.TOP,0,0);
//                                    t.show();
//                                    L.i( query+"是可回收垃圾" );
//                                    break;
//                                case 2:
//                                    t=Toast.makeText( MainActivity.this, "有害垃圾" ,Toast.LENGTH_LONG);
//                                    t.setGravity( Gravity.TOP,0,0);
//                                    t.show();
//                                    L.i( query+"有害垃圾" );
//                                    break;
//                                case 4:
//                                    t=Toast.makeText( MainActivity.this, "湿垃圾" ,Toast.LENGTH_LONG);
//                                    t.setGravity( Gravity.TOP,0,0);
//                                    t.show();
//                                    L.i( query+"湿垃圾" );
//                                    break;
//                                case 8:
//                                    t=Toast.makeText( MainActivity.this, "干垃圾" ,Toast.LENGTH_LONG);
//                                    t.setGravity( Gravity.TOP,0,0);
//                                    t.show();
//                                    L.i( query+"干垃圾" );
//                                    break;
//                                case 16:
//                                    t=Toast.makeText( MainActivity.this, "大件垃圾" ,Toast.LENGTH_LONG);
//                                    t.setGravity( Gravity.TOP,0,0);
//                                    t.show();
//                                    L.i( query+"大件垃圾" );
//                                    break;
//                                default:
//                                    while (j==searchlist.size()){
//                                        t=Toast.makeText( MainActivity.this, "搜不到你要的结果" ,Toast.LENGTH_LONG);
//                                        t.setGravity( Gravity.TOP,0,0);
//                                        t.show();
//                                    }
//                                    break;
//                            }
//
//                        }
//                        else {
//                            L.i( String.valueOf( garbage.getName().equals( query ) ) );
//                        }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );

        return true;
    }

    //标题栏上的快捷键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_item:
                Toast.makeText(MainActivity.this,R.string.share,Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                //Toast.makeText(MainActivity.this,R.string.item_id_out,Toast.LENGTH_SHORT).show();

                Intent intent=new Intent( MainActivity.this,LoginActivity.class );
                startActivity( intent );
                finish();
                break;

            default:
                break;
        }
        return true;
    }

}
