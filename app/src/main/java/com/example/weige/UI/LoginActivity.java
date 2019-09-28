package com.example.weige.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//登录界面
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        button_register=findViewById(R.id.button_register);
        button_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
