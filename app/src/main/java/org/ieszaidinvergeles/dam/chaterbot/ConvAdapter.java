package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConvAdapter extends RecyclerView.Adapter<ConvAdapter.MyViewHolder> {

    private List<Conversaciones> convs;
    private Context context;

    public ConvAdapter(List<Conversaciones> convs, Context context){
        this.convs = convs;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView conversationDate;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            conversationDate = itemView.findViewById(R.id.conversationDate);
        }
    }

    @NonNull
    @Override
    public ConvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.conversaciones_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConvAdapter.MyViewHolder myViewHolder, int position){
        myViewHolder.conversationDate.setText(convs.get(position).getFecha());
    }

    @Override
    public int getItemCount(){
        return convs.size();
    }


}
