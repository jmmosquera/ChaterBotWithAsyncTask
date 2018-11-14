package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VerChat extends AppCompatActivity {

    private RecyclerView rvChat;
    private RecyclerView.LayoutManager rvChatManager;
    private RecyclerView.Adapter rvChatAdapter;
    static private List<Chat> chats = new ArrayList<Chat>();
    private GestorChat gChat;
    Ayudante helper;


    //Esta vista recibe una id y carga los mensajes correspondientes de su tabla en la base de datos.
    //Después muestra todos los mensajes ordenados por fecha en un RecyclerView.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_chat);
        rvChat = findViewById(R.id.rvChat);
        Intent i = getIntent();
        long idconv = i.getLongExtra("idconv", 0);

        helper = new Ayudante(this);
        gChat = new GestorChat((this));
        gChat.open();

        chats = gChat.select("idconv = '"+idconv+"'");

        rvChatManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(rvChatManager);
        rvChatAdapter = new ChatAdapter(chats, this);
        rvChat.setAdapter(rvChatAdapter);


    }
}
