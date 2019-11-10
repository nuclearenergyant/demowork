package com.example.weige.UI;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import demo.BaseActivity;
import entity.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

//注册界面
public class RegisterActivity extends BaseActivity {
    //phone
    private TextInputLayout mTextInputLayoutID;
    private TextInputEditText mInputEditTextid;
    //password
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mInputEditTextPassword;
    //password again
    private TextInputLayout mTextInputLayoutPassword_again;
    private TextInputEditText mInputEditTextPassword_again;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        //设置可以计数
        mTextInputLayoutID.setCounterEnabled(true);
        //计数的最大值
        mTextInputLayoutID.setCounterMaxLength(11);
        //设计密码不能少于6位数
        mInputEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextInputLayoutPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //设计确认密码不能少于6位数
        mInputEditTextPassword_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextInputLayoutPassword_again.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //设计账号为11位数
        mInputEditTextid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextInputLayoutID.setErrorEnabled(false);

                //mTextInputLayoutPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //点击事件
        //注册点击按钮

        //注册按钮
        Button button_re = findViewById( R.id.re_user );
        button_re.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String name = mInputEditTextid.getText().toString().trim();
                String password = mInputEditTextPassword.getText().toString().trim();
                String password_again = mInputEditTextPassword_again.getText().toString().trim();


                if (TextUtils.isEmpty( name ) || name.length() != 11) {
                    mInputEditTextid.setError( "输入正确手机号码" );
                }
                //判断密码格式
                if (TextUtils.isEmpty( password ) || password.length() < 6) {
                    mInputEditTextPassword.setError( "密码错误不能少于6个字符" );
                }
                //判断确认密码格式
                if (TextUtils.isEmpty( password_again ) || password_again.length() < 6) {
                    mInputEditTextPassword_again.setError( "密码错误不能少于6个字符" );
                }


                //判断是否为空
                if (!TextUtils.isEmpty( name ) & !TextUtils.isEmpty( password )
                        & !TextUtils.isEmpty( password_again )) {
                    //判断两次输入密码是否相同
                    if (password.equals( password_again )) {

                        if (password.trim().length()>=6&&name.trim().length()==11){
                            //注册
                            User myuser = new User();
                            myuser.setPhone( name );
                            myuser.setPassword( password );

                            //开始通过请求进行注册
                            String API_Register= APITOALIYUN.IP_address_register+"phone="+mInputEditTextid.getText().toString()+"&"+"password="+mInputEditTextPassword.getText().toString();
                            L.i( API_Register );
                            OkHttpClient okHttpClient=new OkHttpClient();
                            Request.Builder builder=new Request.Builder();
                            Request request=builder.get().url(API_Register).method( "GET",null ).build();
                            okhttp3.Call call=okHttpClient.newCall(request);
                            L.i("点击注册按钮");
                            try {
                                Response response=call.execute();
                                assert response.body() != null;
                                final String str = response.body().string();
                                L.i( str );

                                JSONObject jsonObject=JSONObject.parseObject( str );
                                L.i( jsonObject.toJSONString() );
                                L.i( String.valueOf( jsonObject.getInteger( "code" ) ) );
                                if (Objects.equals( jsonObject.getInteger( "code" ), 200)) {
                                    L.i( "注册成功" );
                                    Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                                    startActivity( intent );
                                    finish();
                                } else {
                                    Snackbar.make( v, "该账号已经被注册", Snackbar.LENGTH_LONG ).show();
                                    L.i( "注册失败" );
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Snackbar.make( v, "不符合格式，请检查账号为11位，密码不能少于6位", Snackbar.LENGTH_LONG ).show();
                        }

                    } else {
                        mInputEditTextPassword.setError( "两个密码不一致" );
                        mInputEditTextPassword_again.setError( "两个密码不一致" );
                    }

                } else {
                    Snackbar.make( v, "输入框不能为空", Snackbar.LENGTH_LONG ).show();
                }
            }
        } );

    }

    private void initView() {
        //id
        mTextInputLayoutID = findViewById(R.id.text_input_layout_re_id);
        mInputEditTextid = findViewById(R.id.text_input_re_id);
        //password
        mTextInputLayoutPassword = findViewById(R.id.text_input_layout_password);
        mInputEditTextPassword = findViewById(R.id.text_input_password);
        //password again
        mTextInputLayoutPassword_again = findViewById(R.id.text_input_layout_password_again);
        mInputEditTextPassword_again = findViewById(R.id.text_input_password_again);

    }
}
