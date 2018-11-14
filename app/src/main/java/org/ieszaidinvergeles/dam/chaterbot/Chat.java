package org.ieszaidinvergeles.dam.chaterbot;

public class Chat {
    private long id;
    private long idconv;
    private String hora;
    private String emisor;
    private String mensaje;

    public Chat(){
        this(0, 0,"","","");
    }

    public Chat(long id, long idconv, String hora, String emisor, String mensaje){
        this.id =id;
        this.idconv = idconv;
        this.hora = hora;
        this.emisor = emisor;
        this.mensaje = mensaje;
    }

    public long getId() {
        return id;
    }

    public Chat setId(long id) {
        this.id = id;
        return this;
    }

    public long getIdconv() {
        return idconv;
    }

    public Chat setIdconv(long idconv) {
        this.idconv = idconv;
        return this;
    }

    public String getHora() {
        return hora;
    }

    public Chat setHora(String hora) {
        this.hora = hora;
        return this;
    }

    public String getEmisor() {
        return emisor;
    }

    public Chat setEmisor(String emisor) {
        this.emisor = emisor;
        return this;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Chat setMensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", idconv=" + idconv +
                ", hora='" + hora + '\'' +
                ", emisor='" + emisor + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
