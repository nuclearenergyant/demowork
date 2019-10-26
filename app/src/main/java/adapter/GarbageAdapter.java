package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.weige.UI.R;

import java.util.List;

import entity.Garbage;


public class GarbageAdapter extends RecyclerView.Adapter<GarbageAdapter.ViewHolder> {

    private List<Garbage>list;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_name;
        private TextView textView_category;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textView_name=itemView.findViewById( R.id.garbage_name );
            textView_category=itemView.findViewById( R.id.garbage_category );
        }
    }
    public GarbageAdapter(List<Garbage>list_c){
        list=list_c;
    }
    @NonNull
    @Override
    public GarbageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.garbage_fragment ,viewGroup,false);
        ViewHolder holder=new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GarbageAdapter.ViewHolder viewHolder, int i) {

        Garbage garbage=list.get( i );
        viewHolder.textView_name.setText( garbage.getName() );
        //垃圾分类
        switch(garbage.getCategory()){
            case 1:
                viewHolder.textView_category.setText( ( "可回收垃圾" ));
                break;
            case 2:
                viewHolder.textView_category.setText( ( "有害垃圾"  ));
                break;
            case 4:
                viewHolder.textView_category.setText( ( "湿垃圾"  ));
                break;
            case 8:
                viewHolder.textView_category.setText( ( "干垃圾" ));
                break;
            case 16:
                viewHolder.textView_category.setText( ( "大件垃圾" ));
            default:
                break;
        }
        //viewHolder.textView_category.setText( ( "" +garbage.getCategory() ));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
