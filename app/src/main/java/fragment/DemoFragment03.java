package fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.weige.UI.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CardviewForbillAdapter;
import entity.Bill;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import url.APITOALIYUN;
import utils.L;

public class DemoFragment03 extends Fragment {

    //测试cardview

//    private Bill[] bills={
//            new Bill( "123123123","1232" ),
//            new Bill( "123123123","1232" ),
//            new Bill( "123123123","1232" ),
//            new Bill( "123123123","1232" )
//    };
    private List<Bill> bills_list=new ArrayList<>(  );
    private CardviewForbillAdapter cardviewForbillAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_demo_fragment03,null);
        /*测试*/

        RecyclerView recyclerView=view.findViewById( R.id.recycle_for_bill );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( getContext() );

        linearLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        bills_list.clear();

        //请求数据库的数据

        String find_all_Bill= APITOALIYUN.IP_address_check_bill+"UserID="+APITOALIYUN.id;
        L.i( find_all_Bill );
        OkHttpClient okHttpClient=new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        Request request=builder.get().url(find_all_Bill).method( "GET",null ).build();
        okhttp3.Call call=okHttpClient.newCall(request);
        L.i("查询数据bill");
        try {
            Response response=call.execute();
            assert response.body() != null;
            final String str = response.body().string();
            JSONObject jsonObject=JSONObject.parseObject( str );
            L.i( jsonObject.toJSONString() );
            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString( "result" ));
            JSONObject row = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                row = jsonArray.getJSONObject(i);
                Bill bill=new Bill( row.getInteger( "id" ), row.getInteger( "collector_id" ),
                        row.getString( "state" ),row.getString("price") ,row.getDate("date"),
                        row.getString("headquarters"),row.getBoolean("whether_recovery"),row.getBoolean("returnMonenyforcustomer"),
                        row.getDouble( "price_forcustomer" ));

                bills_list.add( bill );
            }
            L.i( String.valueOf( bills_list.size() ) );
        }catch (Exception e){
            e.printStackTrace();
        }

        cardviewForbillAdapter=new CardviewForbillAdapter(bills_list );
        recyclerView.setAdapter( cardviewForbillAdapter );
        return view;
    }
}
