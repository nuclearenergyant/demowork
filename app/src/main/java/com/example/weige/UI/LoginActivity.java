package com.example.weige.UI;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import demo.BaseActivity;
import entity.Garbage;
import entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import url.APITOALIYUN;
import utils.L;

//登录界面
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    String phone_client;
    private TextInputEditText phone;
    private TextInputEditText password;

    private TextInputLayout phone_layout;
    private Button button_login;
    private Button button_register;
    private CheckBox checkBox;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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

        //记住密码复选框
        checkBox=findViewById( R.id.checkbox );
        button_register=findViewById(R.id.button_register);
        button_login=findViewById( R.id.button_login );
        preferences=getPreferences(MODE_PRIVATE);
        //两个username/password输入框
        phone=findViewById( R.id.text_input_re_id_login );
        password=findViewById( R.id.text_input_password_login );
        phone_layout=findViewById( R.id.text_input_layout_re_id_login );


        button_register.setOnClickListener(this);
        button_login.setOnClickListener( this );

        //读取密码
        boolean is_or_remember=preferences.getBoolean("checkbox",false);
        if (is_or_remember){
            String id=preferences.getString("id","");
            String pass=preferences.getString("password","");
            phone.setText(id);
            password.setText(pass);
            checkBox.setChecked(true);
            Log.i("receiver","因为上一次选框被选中，账号密码被填写");
        }

        //设计账号为11位数
        //设置可以计数
        phone_layout.setCounterEnabled(true);
        //计数的最大值
        phone_layout.setCounterMaxLength(11);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.button_register:
                startActivity( new Intent( this, RegisterActivity.class ) );
                //实验先不去finish（）；
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
                        //存取密码
                        //设置全局变量User_id
                        JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString( "result" ));
                        JSONObject row = null;

                        row = jsonArray.getJSONObject(0);
                        APITOALIYUN.id=row.getInteger( "id" );

                        String id=phone.getText().toString();
                        String pass=password.getText().toString();
                        editor = preferences.edit();
                        if (checkBox.isChecked()) {
                            editor.putBoolean( "checkbox",true );
                            editor.putString( "id", id );
                            editor.putString( "password", pass);
                            L.i( "选中复选框，保存了密码" );
                        } else {
                            editor.clear();
                            L.i(  "没有选中复选框，没有保存密码" );
                        }
                        editor.apply();
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
                break;
            default:
                break;
        }
    }

    //点解忘记了密码
    public void forget_password(View view) {

        //跳转到修改密码界面
        Intent intent=new Intent( LoginActivity.this,ForgetPassword.class );
        startActivity( intent );

    }
}
