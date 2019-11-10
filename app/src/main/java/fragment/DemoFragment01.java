package fragment;


import android.os.Bundle;
import android.os.StrictMode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.weige.UI.LoginActivity;
import com.example.weige.UI.MainActivity;
import com.example.weige.UI.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import adapter.GarbageAdapter;
import entity.Garbage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

public class DemoFragment01 extends Fragment {

    private List<Garbage>garbageList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_demo_fragment01,null);
        /*解决android报错android.os.NetworkOnMainThreadException*/
        //修改系统策略，放开所有的权限
        //代码添加到onCreate回调方法中即可
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //连接后端，得到服务器的数据
        initgarbage();
        RecyclerView recyclerView=view.findViewById( R.id.re_garbage );
        RecyclerView.LayoutManager manager=new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager( manager );
        GarbageAdapter adapter=new GarbageAdapter(  garbageList);
        recyclerView.setAdapter( adapter );
        return view;
    }

    private void initgarbage() {
        OkHttpClient okHttpClient_garbage=new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        Request request=builder.get().url( APITOALIYUN.Garbage_API).method( "GET",null ).build();
        okhttp3.Call call=okHttpClient_garbage.newCall(request);
        try {
            Response response=call.execute();
            final String str_garbage = response.body().string();
            L.i( str_garbage );
            JSONObject jsonObject=JSONObject.parseObject( str_garbage );
            L.i(  jsonObject.getString( "result"));
            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString( "result" ));
            JSONObject row = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                row = jsonArray.getJSONObject(i);
                Garbage garbage=new Garbage( row.getString( "name" ),row.getInteger( "category" ) );
                garbageList.add( garbage );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
