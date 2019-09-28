package Application;


import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import utils.StaticClass;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_IP, true);

        //初始化Bmob
        Bmob.initialize(this,StaticClass.Bmob_APP_IP);
    }
}
