package com.example.weige.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import utils.ShareUtils;
import utils.StaticClass;
import utils.UtilTools;

//初始登录动画，即闪屏页
public class SplashActivity extends AppCompatActivity{
    /*1.延时2000s
    * 判断程序是否是第一次运行
    * 自定义字体
    * Activity全屏主题
    * */

    private TextView textView;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        Intent intent;
                        intent = new Intent(SplashActivity.this, GuigeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        textView=findViewById(R.id.tv_show);
        //设置字体
        UtilTools.setfonts(this,textView);
    }
    
    //判断是否程序第一次运行
    private Boolean isFirst() {
        boolean isfirst= ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if (isfirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }
}
