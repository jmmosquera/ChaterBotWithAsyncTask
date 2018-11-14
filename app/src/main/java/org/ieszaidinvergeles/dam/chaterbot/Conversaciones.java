package org.ieszaidinvergeles.dam.chaterbot;

public class Conversaciones {

    private long id;
    private String fecha;

    public Conversaciones(){
        this(0, "");
    }

    public Conversaciones(long id, String fecha){
        this.id = id;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public Conversaciones setId(long id) {
        this.id = id;
        return this;
    }

    public String getFecha() {
        return fecha;
    }

    public Conversaciones setFecha(String fecha) {
        this.fecha = fecha;
        return this;
    }


}
