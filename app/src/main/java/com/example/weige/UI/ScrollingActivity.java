package com.example.weige.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;


public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener, LocationSource, AMapLocationListener {
    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象，定位参数
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationListener mLocationListener ;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //定位的字符串
    private String address;

    private Button button_to_add_bill;
    //获取定位信息
    private Button get_address;
    private TextInputLayout textInputLayout_phone;
    private TextInputLayout textInputLayout_message;
    private TextInputLayout textInputLayout_address;

    private TextInputEditText textInputEditText_phone;
    private TextInputEditText textInputEditText_message;
    private TextInputEditText textInputEditText_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_scrolling );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        toolbar.setSubtitleTextAppearance(this,R.style.Toolbar_SubTitleText);

        //初始化
        initView();
        FloatingActionButton fab =  findViewById( R.id.back_to_main );
        fab.setOnClickListener( v -> {
            Intent intent=new Intent( ScrollingActivity.this,MainActivity.class );
            startActivity( intent );
            finish();
        } );

        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"权限已申请";
               get_address_for_bill();
            }
        }
    }

    private void initView() {
        //下单按钮设置监听事件
        button_to_add_bill=findViewById( R.id.button_to_add_new_bill );
        button_to_add_bill.setOnClickListener(  this );
        //获取地址设置监听事件
        get_address=findViewById( R.id.get_address );
        get_address.setOnClickListener( this );

        //手机号码/地址布局初始化
        textInputEditText_phone=findViewById( R.id.text_input_phone_bill );
        textInputEditText_address=findViewById( R.id.add_to_address );

        textInputLayout_phone=findViewById( R.id.text_input_layout_add_bill_phone );
        textInputLayout_address=findViewById( R.id.text_input_layout_add_address );

        //初始化手机号码为11位
        textInputLayout_phone.setCounterEnabled(true);
        //计数的最大值
        textInputLayout_phone.setCounterMaxLength(11);

        //设计手机号不能少于11位数
        textInputEditText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout_phone.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //设计确认密码不能少于6位数
        textInputEditText_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout_address.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_to_add_new_bill:
                //首先先判断手机号码、地址。输入框是否为空
                add_bill(v);
                L.i( "点击按钮_订单" );
                break;
            case R.id.get_address:
                textInputEditText_address.setText( address );

                break;
            default:
                break;
        }
    }

    //添加订单
    private void add_bill(View v) {
        String phone =textInputEditText_phone.getText().toString().trim();
        String address_input=textInputEditText_address.getText().toString().trim();
        if (TextUtils.isEmpty( phone ) || phone.length() != 11) {
            textInputEditText_phone.setError( "输入正确手机号码" );
        }
        if (TextUtils.isEmpty( address_input )){
            textInputEditText_address.setError( "地址不能为空" );
        }

        //判断如果手机号不为空，且号码的长度位11，再且地址不为空
        if (!TextUtils.isEmpty( phone ) ){
            if (!TextUtils.isEmpty( address_input )){
                if (phone.length()==11){
                    //满足下单条件
                    //下单开始
                    String Add_new_to_bill=APITOALIYUN.IP_address_add_new_bill+"UserID="+APITOALIYUN.id
                            +"&phone="+phone+"&bill_address="+address_input;
                    L.i( Add_new_to_bill );
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request.Builder builder=new Request.Builder();
                    Request request=builder.get().url(Add_new_to_bill).method( "GET",null ).build();
                    okhttp3.Call call=okHttpClient.newCall(request);
                    L.i("点击下单按钮");
                    try {
                        Response response=call.execute();
                        assert response.body() != null;
                        final String str = response.body().string();
                        L.i( str );

                        JSONObject jsonObject=JSONObject.parseObject( str );
                        L.i( jsonObject.toJSONString() );
                        L.i( String.valueOf( jsonObject.getInteger( "code" ) ) );
                        if (Objects.equals( jsonObject.getInteger( "code" ), 200)) {
                            L.i( "下单成功" );
                            Intent intent = new Intent( ScrollingActivity.this, MainActivity.class );
                            startActivity( intent );
                            finish();
                        } else {
                            Snackbar.make( v, "下单失败", Snackbar.LENGTH_LONG ).show();
                            L.i( "下单失败" );
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    textInputEditText_phone.setError( "手机号不为11位" );
                }

            }else {
                textInputEditText_address.setError( "地址为空" );
            }


        }
        else {
            //不满足条件，就不能下单成功
            L.i( "内容为空" );
            // showToast( "输入框为空" );
            Snackbar.make(v, "输入框不能为空", Snackbar.LENGTH_LONG ).show();
            textInputEditText_phone.setError( "请检查输入" );

        }
    }

    //获取地址
    private void get_address_for_bill() {
        try {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(50000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {

        }

    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息

//                    获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i("定位类型", amapLocation.getLocationType() + "");
                    Log.i("获取纬度", amapLocation.getLatitude() + "");
                    Log.i("获取经度", amapLocation.getLongitude() + "");
                    Log.i("获取精度信息", amapLocation.getAccuracy() + "");

                    //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i("地址", amapLocation.getAddress());
                    Log.i("国家信息", amapLocation.getCountry());
                    Log.i("省信息", amapLocation.getProvince());
                    Log.i("城市信息", amapLocation.getCity());
                    Log.i("城区信息", amapLocation.getDistrict());
                    Log.i("街道信息", amapLocation.getStreet());
                    Log.i("街道门牌号信息", amapLocation.getStreetNum());
                    Log.i("城市编码", amapLocation.getCityCode());
                    Log.i("地区编码", amapLocation.getAdCode());
                    Log.i("获取当前定位点的AOI信息", amapLocation.getAoiName());
                    Log.i("获取当前室内定位的建筑物Id", amapLocation.getBuildingId());
                    Log.i("获取当前室内定位的楼层", amapLocation.getFloor());
                    Log.i("获取GPS的当前状态", amapLocation.getGpsAccuracyStatus() + "");

                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());

                    Log.i("获取定位时间", df.format(date));

                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = new Date(amapLocation.getTime());
                    df2.format(date1);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码


                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append( amapLocation.getCountry() + ""
                            + amapLocation.getProvince() + ""
                            + amapLocation.getCity() + ""
                            + amapLocation.getProvince() + ""
                            + amapLocation.getDistrict() + ""
                            + amapLocation.getStreet() + ""
                            + amapLocation.getStreetNum() );

                    address=buffer.toString();
                    L.i("地址是:"+address);


                    // 停止定位
                    mlocationClient.stopLocation();
                    mlocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
        }
    }
    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
