package org.ieszaidinvergeles.dam.chaterbot;

import android.app.ActionBar;
import android.provider.BaseColumns;

public class Contrato {

    private Contrato(){}

    public static abstract class TablaConversaciones implements BaseColumns{
        public static final String TABLA="conversaciones";
        public static final String FECHA="fecha";
    }


    public static final String SQL_CREATE_CONVERSACION =
            "create table "+TablaConversaciones.TABLA+"("+
            TablaConversaciones._ID + " integer primary key autoincrement,"+
            TablaConversaciones.FECHA+" TEXT unique)";

    public static final  String SQL_DROP_CONVERSACIONES = "drop table if exists" + TablaConversaciones.TABLA;

    public static abstract class TablaChat implements  BaseColumns{
        public static final String TABLA="chat";
        public static final String IDCONV="idconv";
        public static final String HORA="hora";
        public static final String EMISOR="emisor";
        public static final String MENSAJE="mensaje";
    }

    public static final String SQL_CREATE_CHAT =
            "create table " + TablaChat.TABLA+
            " (" + Contrato.TablaChat._ID +
            " integer primary key autoincrement, " +
            Contrato.TablaChat.IDCONV + " integer, " +
            Contrato.TablaChat.HORA + " text, " +
            Contrato.TablaChat.EMISOR + " text, " +
            Contrato.TablaChat.MENSAJE + " text)";

    public static final String SQL_DROP_CHAT = "drop table if exists" + TablaChat.TABLA;
}
