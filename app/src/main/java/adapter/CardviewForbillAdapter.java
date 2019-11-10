package adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weige.UI.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.Bill;

public class CardviewForbillAdapter extends RecyclerView.Adapter<CardviewForbillAdapter.ViewHolder> {
    private Context context;
    private List<Bill>list;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView_time_state;
        TextView textView_date;
        TextView textView_title;
        TextView textVie_content;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            cardView= (CardView) itemView;
            textView_time_state=itemView.findViewById( R.id.time_state );
            textView_date=itemView.findViewById( R.id.time_bill );
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
        viewHolder.textView_title.setText(String.valueOf( bill.getId() ) );
        viewHolder.textVie_content.setText(  bill.getPrice() );
        viewHolder.textView_time_state.setText( bill.getState() );
        String time=new SimpleDateFormat( "yyyy-MM-dd    HH:mm:ss" ).format( bill.getDate() );
        viewHolder.textView_date.setText(time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
