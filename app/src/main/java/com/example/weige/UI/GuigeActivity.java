package com.example.weige.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import utils.L;
//动画界面，即三页介绍页面
public class GuigeActivity extends AppCompatActivity implements View.OnClickListener {

    //页面容器
    private ViewPager viewPager;
    //容器页面
    private View view1,view2,view3;
    private List<View> list=new ArrayList<>();
    //跳过进入首页键
    private Button imageView;

    //小圆点
    private ImageView point1,point2,point3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guige2);

        initview();
    }

    private void initview() {
        viewPager=findViewById(R.id.myviewpager);
        view1=View.inflate(this,R.layout.page_item_one,null);
        view2=View.inflate(this,R.layout.page_item_two,null);
        view3=View.inflate(this,R.layout.page_item_three,null);

        view3.findViewById(R.id.button_start).setOnClickListener(this);

        imageView=findViewById(R.id.back_into);
        imageView.setOnClickListener(this);


        list.add(view1);
        list.add(view2);
        list.add(view3);

        //设置适配器
        viewPager.setAdapter(new GuidAdpater());

        //设置小圆点
        point1=findViewById(R.id.image_point);
        point2=findViewById(R.id.image_point2);
        point3=findViewById(R.id.image_point3);
        //监听ViewPager的滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            //pager切换
            @Override
            public void onPageSelected(int i) {

                L.i("position"+i);
                switch (i){
                    case 0:
                        setPoint(true,false,false);
                        imageView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPoint(false,true,false);
                        imageView.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        setPoint(false,false,true);
                        imageView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start:
            case R.id.back_into:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    public class GuidAdpater extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view ==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(list.get(position));

        }
    }

    //设置小圆点显示效果
    private void setPoint(boolean ischeck1,boolean ischeck2,boolean ischeck3){
        if (ischeck1)
            point1.setImageResource(R.drawable.blue);
        else point1.setImageResource(R.drawable.yellow);

        if (ischeck2)
            point2.setImageResource(R.drawable.blue);
        else point2.setImageResource(R.drawable.yellow);

        if (ischeck3)
            point3.setImageResource(R.drawable.blue);
        else point3.setImageResource(R.drawable.yellow);

    }
}
