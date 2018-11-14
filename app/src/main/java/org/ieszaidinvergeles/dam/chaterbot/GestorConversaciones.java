package org.ieszaidinvergeles.dam.chaterbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GestorConversaciones {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorConversaciones(Context c){
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

    public long insert(Conversaciones conv){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaConversaciones.FECHA, conv.getFecha());

        long id = bd.insert(Contrato.TablaConversaciones.TABLA, null, valores);

        return id;
    }

    public int delete(Conversaciones conv){
        String condicion = Contrato.TablaConversaciones._ID + " = ?";
        String[] argumentos = { conv.getId() + ""};
        int cuenta = bd.delete(Contrato.TablaConversaciones.TABLA, condicion, argumentos);

        return cuenta;
    }

    public int update(Conversaciones conv){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaConversaciones.FECHA, conv.getFecha());
        String condicion = Contrato.TablaConversaciones._ID + " = ?";
        String[] argumentos = { conv.getId() + "" };

        int cuenta  = bd.update(Contrato.TablaConversaciones.TABLA, valores, condicion, argumentos);

        return cuenta;

    }

    public Cursor getCursor(){
        Cursor cursor = bd.query(Contrato.TablaConversaciones.TABLA, null, null, null, null, null, null);
        return cursor;
    }



    public Conversaciones getRow(Cursor c){
        Conversaciones conv = new Conversaciones();
        conv.setId(c.getLong(0));
        conv.setFecha(c.getString(1));

        return conv;
    }

    public Conversaciones get(long id){
        String[] proyeccion = { Contrato.TablaConversaciones._ID, Contrato.TablaConversaciones.FECHA};
        String where = Contrato.TablaConversaciones._ID+" = ?";
        String[] parametros = new String[] {id+""};
        String groupby = null;
        String having = null;
        String orderby = Contrato.TablaConversaciones.FECHA + " ASC";

        Cursor c = bd.query(Contrato.TablaConversaciones.TABLA, proyeccion, where, parametros, groupby, having, orderby);
        c.moveToFirst();

        Conversaciones conv = getRow(c);

        c.close();

        return conv;
    }

    public Conversaciones getRow(String fecha){
        String[] parametros = new String[]{fecha};
        Cursor c = bd.rawQuery("select * from "+Contrato.TablaConversaciones.TABLA+
        " where "+Contrato.TablaConversaciones.FECHA + " = ?", parametros);
        c.moveToFirst();
        Conversaciones conv = getRow(c);
        c.close();
        return conv;
    }

    public List<Conversaciones> select(String condicion){
        List<Conversaciones> lc = new ArrayList<Conversaciones>();
        Cursor c = bd.query(Contrato.TablaConversaciones.TABLA, null, condicion, null, null, null, null);
        Conversaciones conv;
        while(c.moveToNext()){
            conv = getRow(c);
            lc.add(conv);
        }

        c.close();
        return lc;
    }

}
