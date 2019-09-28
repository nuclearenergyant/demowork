package com.example.weige.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import entity.Myuser;

//注册界面
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    //注册按钮
    private Button button_re;
    //性别
    private RadioGroup radioGroup;
    //定义性别
    private boolean isGender = true;
    //id
    private TextInputLayout mTextInputLayoutID;
    private TextInputEditText mInputEditTextid;
    //password
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mInputEditTextPassword;
    //password again
    private TextInputLayout mTextInputLayoutPassword_again;
    private TextInputEditText mInputEditTextPassword_again;
    //邮箱
    private TextInputLayout mTextInputLayoutemail;
    private TextInputEditText mInputEditTextemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "e3d0980acbae185e17fafc3c4426b0d6");
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
                //mTextInputLayoutID.setErrorEnabled(false);
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
                //mTextInputLayoutID.setErrorEnabled(false);
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

        //设计邮箱
        mInputEditTextemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextInputLayoutemail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.re_button).setOnClickListener(this);

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
        //邮箱
        mTextInputLayoutemail = findViewById(R.id.text_input_layout_email);
        mInputEditTextemail = findViewById(R.id.text_input_email);

        //注册点击按钮
        button_re = findViewById(R.id.re_button);

        //性别选择按钮
        radioGroup = findViewById(R.id.radio);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_button:
                String name = mInputEditTextid.getText().toString().trim();
                String password = mInputEditTextPassword.getText().toString().trim();
                String password_again = mInputEditTextPassword_again.getText().toString().trim();
                String email = mInputEditTextemail.getText().toString().trim();

                if (TextUtils.isEmpty(name) || name.length() !=11) {
                    mInputEditTextid.setError("输入正确手机号码");
                }
                //判断密码格式
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    mInputEditTextPassword.setError("密码错误不能少于6个字符");
                }
                //判断确认密码格式
                if (TextUtils.isEmpty(password_again) || password_again.length() < 6) {
                    mInputEditTextPassword_again.setError("密码错误不能少于6个字符");
                }

                //判断邮箱
                if (TextUtils.isEmpty(email)||!(email.endsWith("qq.com"))) {
                    mInputEditTextemail.setError("注册邮箱必须为qq邮箱");
                }

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(password_again)
                        & !TextUtils.isEmpty(email)) {
                    //判断两次输入密码是否相同
                    if (password.equals(password_again)) {
                        //先把性别给判断一下
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.radioButton_boy) {
                                    isGender = true;
                                } else if (checkedId == R.id.radioButton_girl) {
                                    isGender = false;
                                }
                            }
                        });
                        //注册
                        Myuser myuser=new Myuser();
                        myuser.setUsername(name);
                        myuser.setPassword(password);
                        myuser.setSex(isGender);
                        myuser.setEmail(email);

                        myuser.signUp(new SaveListener<Myuser>() {

                            @Override
                            public void done(Myuser myuser, BmobException e) {

                                if (e == null) {
                                    Snackbar.make(button_re, "注册成功：", Snackbar.LENGTH_LONG).show();
                                   finish();
                                }
                                else
                                {
                                    Snackbar.make(button_re, "注册失败"+e.toString(), Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        mInputEditTextPassword.setError("两个密码不一致");
                        mInputEditTextPassword_again.setError("两个密码不一致");
                    }

                } else {
                    Toast.makeText(this, "输入框不为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
