package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chaterbot.db";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contrato.SQL_CREATE_CONVERSACION);
        Log.v("ZZZ", Contrato.SQL_CREATE_CONVERSACION);
        db.execSQL(Contrato.SQL_CREATE_CHAT);

        /*String sql;
        sql="create table " + Contrato.TablaConversaciones.TABLA +
                " (" + Contrato.TablaConversaciones._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaConversaciones.FECHA + " text unique)";
        sql+="create table " + Contrato.TablaChat.TABLA +
                " (" + Contrato.TablaChat._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaChat.IDCONV + " integer, " +
                Contrato.TablaChat.HORA + " text, " +
                Contrato.TablaChat.EMISOR + " text, " +
                Contrato.TablaChat.MENSAJE + " text)";
        db.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Contrato.SQL_DROP_CONVERSACIONES);
        db.execSQL(Contrato.SQL_DROP_CHAT);
        
        onCreate(db);
    }
}
