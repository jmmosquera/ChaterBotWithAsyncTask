package org.ieszaidinvergeles.dam.chaterbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GestorChat {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorChat(Context c){
        abd = new Ayudante(c);
    }

    public void open(){
        bd = abd.getWritableDatabase();
    }

    public void openRead(){
        bd = abd.getReadableDatabase();
    }

    public void close(){
        abd.close();
    }

    public long insert(Chat ch){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaChat.IDCONV, ch.getIdconv());
        valores.put(Contrato.TablaChat.HORA, ch.getHora());
        valores.put(Contrato.TablaChat.EMISOR, ch.getEmisor());
        valores.put(Contrato.TablaChat.MENSAJE, ch.getMensaje());

        long id = bd.insert(Contrato.TablaChat.TABLA, null, valores);

        return id;
    }

    public int delete(Chat ch){
        String condicion = Contrato.TablaChat._ID + " = ?";
        String[] argumentos = {ch.getId()+""};
        int cuenta = bd.delete(Contrato.TablaChat.TABLA, condicion, argumentos);
        return cuenta;

    }

    public int update(Chat ch){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaChat.IDCONV, ch.getIdconv());
        valores.put(Contrato.TablaChat.HORA, ch.getHora());
        valores.put(Contrato.TablaChat.EMISOR, ch.getEmisor());
        valores.put(Contrato.TablaChat.MENSAJE, ch.getMensaje());

        String condicion = Contrato.TablaChat._ID + " = ?";
        String[] argumentos = {ch.getId()+""};

        int cuenta = bd.update(Contrato.TablaChat.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public Cursor getCursor(){
        Cursor cursor = bd.query(Contrato.TablaChat.TABLA, null, null, null, null, null, null);
        return cursor;
    }

    public Chat getRow(Cursor c){
        Chat ch = new Chat();
        ch.setId(c.getLong(0));
        ch.setIdconv(c.getLong(1));
        ch.setHora(c.getString(2));
        ch.setEmisor(c.getString(3));
        ch.setMensaje(c.getString(4));
        return ch;
    }

    public Chat get(long id){
        String[] proyeccion = {
                Contrato.TablaChat._ID,
                Contrato.TablaChat.IDCONV,
                Contrato.TablaChat.HORA,
                Contrato.TablaChat.EMISOR,
                Contrato.TablaChat.MENSAJE
        };

        String where = Contrato.TablaChat._ID + " = ?";
        String[] parametros = new String[]{id+""};
        String groupby = null;
        String having = null;
        String orderby = Contrato.TablaChat.HORA + " ASC";
        Cursor c = bd.query(Contrato.TablaChat.TABLA, proyeccion, where, parametros, groupby, having, orderby);
        c.moveToFirst();
        Chat ch = getRow(c);
        c.close();

        return ch;
    }

    public List<Chat> select(String condicion){
        List<Chat> lc = new ArrayList<Chat>();
        Cursor c = bd.query(Contrato.TablaChat.TABLA, null, condicion, null, null, null, null);
        Chat ch;
        while(c.moveToNext()){
            ch = getRow(c);
            lc.add(ch);
        }

        c.close();
        return lc;

    }
}
