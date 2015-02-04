package com.draw_lessons.app.canvas;


import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by Adrian on 27/01/2015.
 * <p/>
 * Clase destinada al manejo de la lista
 * de Paths cuando se destruye el activity de dibujo
 * <p/>
 * clase utilizada a modo de Singleton.
 */
public class path_handler {

    private static path_handler handler;
    private ArrayList<Path> list;
    private ArrayList<Integer> list2;

    private path_handler() {
    }


    /**
     * Obtiene una instancia del Objeto
     *
     * @return
     */
    public static path_handler getInstance() {
        if (handler == null)
            handler = new path_handler();
        return handler;
    }

    /**
     * Obtiene la Lista de Objetos
     *
     * @return
     */
    public ArrayList<Path> getList() {
        return list;
    }

    public ArrayList<Integer> getList2() {
        return this.list2;
    }

    /**
     * Establece una lista de Objetos
     *
     * @param list
     */
    public void setList(ArrayList<Path> list) {
        this.list = list;
    }

    public void setList2(ArrayList<Integer> list) {

        this.list2 = list;
    }
}