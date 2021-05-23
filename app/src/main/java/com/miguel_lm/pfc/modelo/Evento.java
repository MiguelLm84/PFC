package com.miguel_lm.pfc.modelo;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Evento {

    private String uid;
    private String titulo;
    private String body;
    private String fecha;
    private String hora;
    private URL imagen;

    public Evento(String uid, String titulo, String body, String fecha, String hora, URL imagen) {
        this.uid = uid;
        this.titulo = titulo;
        this.body = body;
        this.fecha = fecha;
        this.hora = hora;
        //this.imagen = imagen;
    }

    public Evento(String uid, String titulo, String fecha, String hora /*, URL imagen*/) {
        this.uid = uid;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        //this.imagen = imagen;
    }

    public Evento(String uid, String titulo, String body, String fecha, String hora) {
        this.uid = uid;
        this.titulo = titulo;
        this.body = body;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public URL getImagen() {
        return imagen;
    }

    public void setImagen(URL imagen) {
        this.imagen = imagen;
    }
}
