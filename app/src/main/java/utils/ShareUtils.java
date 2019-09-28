package utils;

import android.content.Context;
import android.content.SharedPreferences;
//SharePrenference封装
public class ShareUtils {
   /* private void test(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("key","value");
        editor.commit();
    }*/

   public static final String NAME="config";
   //放文件
   public static void putString(Context context,String key,String value)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      sharedPreferences.edit().putString(key,value).commit();
   }
   //读文件
   public static String getString(Context context,String key,String value)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      return sharedPreferences.getString(key,value);
   }

   //放文件
   public static void putInt(Context context,String key,int value)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      sharedPreferences.edit().putInt(key,value).commit();
   }
   //读文件
   public static int getInt(Context context,String key,int defvalue)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      return sharedPreferences.getInt(key,defvalue);
   }
   //放文件
   public static void putBoolean(Context context,String key,Boolean value)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      sharedPreferences.edit().putBoolean(key,value).commit();
   }
   //读文件
   public static Boolean getBoolean(Context context,String key,Boolean defvalue)
   {
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      return sharedPreferences.getBoolean(key,defvalue);
   }

   //删除单个
   public static void deleteShare(Context context,String key){
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      sharedPreferences.edit().remove(key).commit();
   }
   //删除全部
   public  static void deleteAll(Context context){
      SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
      sharedPreferences.edit().clear().commit();
   }
}
