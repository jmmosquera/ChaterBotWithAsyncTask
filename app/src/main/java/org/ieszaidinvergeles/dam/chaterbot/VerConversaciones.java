package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VerConversaciones extends AppCompatActivity {
    private RecyclerView rvConv;
    private RecyclerView.LayoutManager rvConvManager;
    private RecyclerView.Adapter rvConvAdapter;
    static private List<Conversaciones> convs = new ArrayList<Conversaciones>();
    private GestorConversaciones gConv;
    private GestorChat gChat;
    Ayudante helper;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_conversaciones);
        rvConv = findViewById(R.id.rvConv);
        emptyText = findViewById(R.id.empty_view);

        loadListOfDates();
    }


    //Este método carga desde la base de datos la lista de conexiones que se han realizado al bot,
    //ordenadas por fecha. Después, descarta aquellas conexiones sin mensajes y muestra el resto,
    //permitiendo acceder a ellas.
    //En caso de que no hubiera ninguna conexión al bot, mostraría un mensaje.
    public void loadListOfDates(){
        helper = new Ayudante(this);
        gConv = new GestorConversaciones(this);
        gChat = new GestorChat((this));
        gConv.open();
        gChat.open();

        convs = gConv.select(null);
        for(int i = convs.size()-1; i>=0; i--){
            long id = convs.get(i).getId();
            List<Chat> chats = gChat.select("idconv = '"+id+"'");
            if(chats.size()==0){
                convs.remove(i);
            }
        }

        if(convs.isEmpty()||convs.equals(null)){
            rvConv.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }else{
            rvConv.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);

            rvConvManager = new LinearLayoutManager(this);
            rvConv.setLayoutManager(rvConvManager);
            rvConvAdapter = new ConvAdapter(convs, this);
            rvConv.setAdapter(rvConvAdapter);

            setListeners();
        }
    }

    public void setListeners(){
        final GestureDetector mGestureDetector = new GestureDetector(VerConversaciones.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        rvConv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);
                        long id = convs.get(position).getId();
                        Intent intent = new Intent(VerConversaciones.this, VerChat.class);
                        intent.putExtra("idconv", id);
                        startActivity(intent);

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }
}
