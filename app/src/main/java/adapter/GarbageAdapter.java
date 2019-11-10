package adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weige.UI.R;

import java.util.List;

import entity.Garbage;


public class GarbageAdapter extends RecyclerView.Adapter<GarbageAdapter.ViewHolder> {

    private List<Garbage>list;
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView_name;
        TextView textView_category;
        public ViewHolder( View itemView) {
            super( itemView );
            cardView= (CardView) itemView;
            textView_name=itemView.findViewById( R.id.garbage_name );
            textView_category=itemView.findViewById( R.id.garbage_category );
        }
    }
    public GarbageAdapter(List<Garbage>list_c){
        list=list_c;
    }

    @Override
    public GarbageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.garbage_fragment ,viewGroup,false);
        ViewHolder holder=new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder( GarbageAdapter.ViewHolder viewHolder, int i) {

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
                break;
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
