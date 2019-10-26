package fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weige.UI.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.CardviewForbillAdapter;
import entity.Bill;

public class DemoFragment03 extends Fragment {

    //测试cardview

    private Bill[] bills={
            new Bill( "123123123","1232" ),
            new Bill( "123123123","1232" ),
            new Bill( "123123123","1232" ),
            new Bill( "123123123","1232" )
    };
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
        for (int i =0;i<bills.length;i++){
            bills_list.add(bills[i]);
        }
        cardviewForbillAdapter=new CardviewForbillAdapter(bills_list );
        recyclerView.setAdapter( cardviewForbillAdapter );
        return view;
    }
}
