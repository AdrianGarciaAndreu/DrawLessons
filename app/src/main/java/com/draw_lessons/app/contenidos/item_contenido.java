package com.draw_lessons.app.contenidos;

/**
 * Created by javichaques on 2/2/15.
 */
public class item_contenido {
    private int id_contenido;
    private String nombre_contenido;
    private String texto_contenido;
    private String imagen_contenido;
    private String video_contenido;
    private String enlace_contenido;
    private int duracion_contenido;

    public item_contenido(int id, String nombre, String texto, String imagen, String video, String enlace, int duracion) {
        this.id_contenido = id;
        this.nombre_contenido = nombre;
        this.texto_contenido = texto;
        this.imagen_contenido = imagen;
        this.video_contenido = video;
        this.enlace_contenido = enlace;
        this.duracion_contenido = duracion;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getNombre_contenido() {
        return nombre_contenido;
    }

    public void setNombre_contenido(String nombre_contenido) {
        this.nombre_contenido = nombre_contenido;
    }

    public String getTexto_contenido() {
        return texto_contenido;
    }

    public void setTexto_contenido(String texto_contenido) {
        this.texto_contenido = texto_contenido;
    }

    public String getImagen_contenido() {
        return imagen_contenido;
    }

    public void setImagen_contenido(String imagen_contenido) {
        this.imagen_contenido = imagen_contenido;
    }

    public String getEnlace_contenido() {
        return enlace_contenido;
    }

    public void setEnlace_contenido(String enlace_contenido) {
        this.enlace_contenido = enlace_contenido;
    }

    public int getDuracion_contenido() {
        return duracion_contenido;
    }

    public void setDuracion_contenido(int duracion_contenido) {
        this.duracion_contenido = duracion_contenido;
    }

    public String getVideo_contenido() {
        return video_contenido;
    }

    public void setVideo_contenido(String video_contenido) {
        this.video_contenido = video_contenido;
    }
}