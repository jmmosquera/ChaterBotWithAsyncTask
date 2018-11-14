package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Chat> chats;
    private Context context;

    public ChatAdapter(List<Chat> chats, Context context){
        this.chats = chats;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvEmisor;
        TextView tvHora;
        TextView tvMensaje;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvEmisor = itemView.findViewById(R.id.tvEmisor);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item, viewGroup, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder myViewHolder, int position){
        myViewHolder.tvEmisor.setText(chats.get(position).getEmisor());
        myViewHolder.tvHora.setText(chats.get(position).getHora());
        myViewHolder.tvMensaje.setText(chats.get(position).getMensaje());
    }

    @Override
    public int getItemCount(){
        return chats.size();
    }


}
