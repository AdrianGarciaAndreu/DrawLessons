package com.draw_lessons.app.canvas;

import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by Theidel on 31/01/2015.
 */

/**
 * Created by Adrian on 27/01/2015.
 * <p/>
 * Clase destinada al manejo de la lista
 * de Paths cuando se destruye el activity de dibujo
 * <p/>
 * clase utilizada a modo de Singleton.
 */
public class save_paths {

    private static save_paths handler;
    private ArrayList<Path> list;
    private ArrayList<Integer> list2;
    private ArrayList<circle> list3;

    private save_paths() {
    }


    /**
     * Obtiene una instancia del Objeto
     *
     * @return
     */
    public static save_paths getInstance() {
        if (handler == null)
            handler = new save_paths();
        return handler;
    }

    /**
     * Obtiene la Lista de Objetos
     *
     * @return
     */
    public ArrayList<Path> getList() {
        return this.list;
    }

    public ArrayList<Integer> getList2() {
        return this.list2;
    }

    public ArrayList<circle> getList3() {
        return this.list3;
    }

    /**
     * Lista de Trazos de todas las herramientas
     *
     * @param list
     */
    public void setList(ArrayList<Path> list) {
        this.list = list;
    }


    /**
     * Lista de Trazos de la goma de borrar
     */
    public void setListB(ArrayList<Integer> list) {

        this.list2 = list;
    }


    public void setListC(ArrayList<circle> list) {

        this.list3 = list;

    }

}
