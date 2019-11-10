package utils;

import android.util.Log;

//封装LOG类
public class L {
    public static final boolean DEBYG=true;
    public static final String TAG="DEMO_WORK";

    //五个等级 DEIWF

    public static void d(String test){
        if (DEBYG)
            Log.d(TAG,test);
    }

    public static void e(String test){
        if (DEBYG)
            Log.e(TAG,test);
    }
    public static void i(String test){
        if (DEBYG)
            Log.i(TAG, test );
    }
    public static void w(String test){
        if (DEBYG)
            Log.w(TAG,test);
    }

}
