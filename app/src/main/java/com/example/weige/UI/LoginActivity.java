package com.example.weige.UI;

import android.content.Intent;

import android.os.Build;
import android.os.StrictMode;

import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import url.APITOALIYUN;
import utils.L;

//登录界面
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String phone_client;
    private EditText phone;
    private EditText password;
    private Button button_login;
    private Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*解决android报错android.os.NetworkOnMainThreadException*/
        //修改系统策略，放开所有的权限
        //代码添加到onCreate回调方法中即可
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
    }

    private void initView() {

        button_register=findViewById(R.id.button_register);
        button_login=findViewById( R.id.button_login );

        //两个username/password输入框
        phone=findViewById( R.id.editText_username );
        password=findViewById( R.id.editText_password );
        button_register.setOnClickListener(this);
        button_login.setOnClickListener( this );
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.button_register:
                startActivity( new Intent( this, RegisterActivity.class ) );
                finish();
                break;
            case R.id.button_login:
                String API_login= APITOALIYUN.IP_address_login+"phone="+phone.getText().toString()+"&"+"password="+password.getText().toString();
                L.i( API_login );
                OkHttpClient okHttpClient=new OkHttpClient();
                Request.Builder builder=new Request.Builder();
                Request request=builder.get().url(API_login).method( "GET",null ).build();
                okhttp3.Call call=okHttpClient.newCall(request);
                L.i("点击按钮");
                try {
                    Response response=call.execute();
                    assert response.body() != null;
                    final String str = response.body().string();
                    L.i( str );

                    JSONObject jsonObject=JSONObject.parseObject( str );
                    L.i( jsonObject.toJSONString() );
                    L.i( String.valueOf( jsonObject.getInteger( "code" ) ) );
                    if (Objects.equals( jsonObject.getInteger( "code" ), 200)) {
                        L.i( "登录成功" );
                        Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                        startActivity( intent );
                        finish();
                    } else {
                        Snackbar.make( v, "账号密码错误，或者没有注册", Snackbar.LENGTH_LONG ).show();
                        L.i( "登录失败" );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


//                call.enqueue( new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        L.i("请求失败");
//                    }
//
//                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        final String str = response.body().string();
//                        try {
//                            JSONArray jsonArray = new JSONArray( str );
//                            L.i( str );
//                            int phone_from_B = jsonArray.getJSONObject( 0 ).getInt( "code" );
//                            L.i( "phone_from_B:" + phone_from_B );
//                            if (Objects.equals( phone_from_B, 200)) {
//                                L.i( "登录成功" );
//                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
//                                startActivity( intent );
//                                finish();
//                            } else {
//                                Snackbar.make( v, "这是一个snackbar", Snackbar.LENGTH_SHORT ).show();
//                                L.i( "登录失败" );
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } );
//                call.enqueue(new okhttp3.Callback() {
//                    @Override
//                    public void onFailure(@NonNull okhttp3.Call call, IOException e) {
//                        L.i("请求失败");
//                    }
//                    @Override
//                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//                        final String str = response.body().string();
//                        try {
//                            JSONArray jsonArray = new JSONArray( str );
//                            L.i( str );
//                            String phone_from_B = jsonArray.getJSONObject( 0 ).getString( "code" );
//                            L.i( "phone_from_B:" + phone_from_B );
//                            if (Objects.equals( phone_from_B, "200" )) {
//                                L.i( "登录成功" );
//                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
//                                startActivity( intent );
//                                finish();
//                            } else {
//                                Snackbar.make( v, "这是一个snackbar", Snackbar.LENGTH_SHORT ).show();
//                                L.i( "登录失败" );
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                        runOnUiThread(new Runnable() {
//                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                            @Override
//                            public void run() {
//                                try {
//                                    JSONArray jsonArray=new JSONArray( str );
//                                    L.i( str );
//                                    String phone_from_B=jsonArray.getJSONObject( 0 ).getString( "code" );
//                                    L.i( "phone_from_B:"+phone_from_B );
//                                    if (Objects.equals( phone_from_B,"200" )){
//                                        L.i( "登录成功" );
//                                        Intent intent=new Intent( LoginActivity.this,MainActivity.class );
//                                        startActivity( intent );
//                                        finish();
//                                    }
//                                    else {
//                                        Snackbar.make(v, "这是一个snackbar", Snackbar.LENGTH_SHORT).show();
//                                        L.i( "登录失败" );
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });

                break;
            default:
                break;
        }
    }
}
