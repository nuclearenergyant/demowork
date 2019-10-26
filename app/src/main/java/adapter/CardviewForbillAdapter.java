package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weige.UI.R;

import java.util.List;

import entity.Bill;

public class CardviewForbillAdapter extends RecyclerView.Adapter<CardviewForbillAdapter.ViewHolder> {
    private Context context;
    private List<Bill>list;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView_title;
        TextView textVie_content;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            cardView= (CardView) itemView;
            textView_title=itemView.findViewById( R.id.title );
            textVie_content=itemView.findViewById( R.id.content );
        }
    }
    //构造方法
    public CardviewForbillAdapter (List<Bill> list_a){
        this.list=list_a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context==null){
            context=viewGroup.getContext();
        }
        View view= LayoutInflater.from( context ).inflate( R.layout.cardview_for_bill,viewGroup,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Bill bill=list.get( i );
        viewHolder.textView_title.setText( bill.getId() );
        viewHolder.textVie_content.setText(  bill.getPrice() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
