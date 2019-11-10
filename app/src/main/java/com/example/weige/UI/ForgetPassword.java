package com.example.weige.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.zip.Inflater;

import demo.BaseActivity;
import entity.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

public class ForgetPassword extends BaseActivity{
    private TextInputEditText phone;
    private TextInputEditText password;
    private TextInputEditText password_again;

    private TextInputLayout layout_forget_update_password_view_phone;
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputLayout mTextInputLayoutPassword_again_forget;

    private Button button_to_update_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forget_password );

        phone=findViewById( R.id.text_input_forget_id );
        password=findViewById( R.id.text_input_password_forget );
        password_again=findViewById( R.id.text_input_password_again_forget );
        button_to_update_password=findViewById( R.id.update_password );

        layout_forget_update_password_view_phone=findViewById( R.id.layout_forget_update_password );
        mTextInputLayoutPassword=findViewById( R.id.text_input_layout_password_forget );
        mTextInputLayoutPassword_again_forget=findViewById( R.id.text_input_layout_password_again_forget );
        //设置可以计数
        layout_forget_update_password_view_phone.setCounterEnabled(true);
        //计数的最大值
        layout_forget_update_password_view_phone.setCounterMaxLength(11);

        button_to_update_password.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_password(v);
            }
        } );


    }


    //修改密码
    public void forget_password(View v){

        String name = phone.getText().toString().trim();
        String password_forget = password.getText().toString().trim();
        String password_again_forget = password_again.getText().toString().trim();


        if (TextUtils.isEmpty( name ) || name.length() != 11) {
            phone.setError( "输入正确手机号码" );
        }
        //判断密码格式
        if (TextUtils.isEmpty( password_forget ) || password.length() < 6) {
            password.setError( "密码错误不能少于6个字符" );
        }
        //判断确认密码格式
        if (TextUtils.isEmpty( password_again_forget ) || password_again.length() < 6) {
            password_again.setError( "密码错误不能少于6个字符" );
        }


        //判断是否为空
        if (!TextUtils.isEmpty( name ) & !TextUtils.isEmpty( password_forget )
                & !TextUtils.isEmpty( password_again_forget )) {
            //判断两次输入密码是否相同
            if (password_forget.equals( password_again_forget )) {

                if (password_forget.trim().length()>=6&&name.trim().length()==11){
                    //修改密码
                    User myuser = new User();
                    myuser.setPhone( name );
                    myuser.setPassword( password_forget );

                    //开始通过请求进行修改密码
                    String API_Register= APITOALIYUN.IP_address_forget_password+"phone="+phone.getText().toString()+"&"+"password="+password.getText().toString();
                    L.i( API_Register );
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request.Builder builder=new Request.Builder();
                    Request request=builder.get().url(API_Register).method( "GET",null ).build();
                    okhttp3.Call call=okHttpClient.newCall(request);
                    L.i("点击忘记密码按钮");
                    try {
                        Response response=call.execute();
                        assert response.body() != null;
                        final String str = response.body().string();
                        L.i( str );

                        JSONObject jsonObject=JSONObject.parseObject( str );
                        L.i( jsonObject.toJSONString() );
                        L.i( String.valueOf( jsonObject.getInteger( "code" ) ) );
                        if (Objects.equals( jsonObject.getInteger( "code" ), 200)) {
                            L.i( "修改成功" );
                            Intent intent = new Intent( ForgetPassword.this, LoginActivity.class );
                            startActivity( intent );
                            finish();
                        } else {
                            Snackbar.make( v, "该账号没有被修改成功", Snackbar.LENGTH_LONG ).show();
                            L.i( "修改失败" );
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Snackbar.make( v, "不符合格式，请检查账号为11位，密码不能少于6位", Snackbar.LENGTH_LONG ).show();
                }

            } else {
                password.setError( "两个密码不一致" );
                password_again.setError( "两个密码不一致" );
            }

        } else {
            Snackbar.make( v, "输入框不能为空", Snackbar.LENGTH_LONG ).show();
        }

    }

}
