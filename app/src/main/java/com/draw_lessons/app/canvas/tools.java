package com.draw_lessons.app.canvas;

import android.graphics.Canvas;

/**
 * Created by Adrian on 30/01/2015.
 * ^
 * Clase de métodos estaticos
 * para seleccionar la herramienta de uso en el
 * Canvas
 */
public class tools {


    /**
     * Método para elegir la herramienta
     * de la regla
     */
    public static void useRuler(canvas c) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                canvas.Compass = false;
                canvas.HandMade = false;
                canvas.Eraser = false;
                canvas.Ruler = true;

                canvas.compasLayer = false;
                canvas.compassBmp = null;
                canvas.compassT1 = false;
                canvas.compassT2 = false;
                canvas.compassT3 = false;
                canvas.circleFixed = false;
                canvas.drawing = true;
                canvas.cnv = new Canvas(canvas.bmp);

                canvas.p.setColor(0xFF000000);
                canvas.p.setStrokeWidth(canvas.SIZE_SMALL);


            }
        }).start();
        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * del compas
     */
    public static void useCompass(canvas c) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                canvas.Ruler = false;
                canvas.HandMade = false;
                canvas.Compass = true;
                canvas.Eraser = false;

                canvas.rulerLayer = false;
                canvas.rulerBmp = null;
                canvas.rulerT1 = false;
                canvas.rulerT2 = false;
                canvas.accepted = false;


                canvas.p.setColor(0xFF000000);
                canvas.p.setStrokeWidth(canvas.SIZE_SMALL);

            }
        }).start();


        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * de Mano alzada
     */
    public static void useHand(canvas c) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                canvas.Ruler = false;
                canvas.HandMade = true;
                canvas.Compass = false;
                canvas.Eraser = false;

                canvas.compasLayer = false;
                canvas.compassBmp = null;
                canvas.compassT1 = false;
                canvas.compassT2 = false;
                canvas.compassT3 = false;
                canvas.circleFixed = false;
                canvas.drawing = true;
                canvas.cnv = new Canvas(canvas.bmp);

                canvas.rulerLayer = false;
                canvas.rulerBmp = null;
                canvas.rulerT1 = false;
                canvas.rulerT2 = false;
                canvas.accepted = false;

                canvas.p.setColor(0xFF000000);
                canvas.p.setStrokeWidth(canvas.SIZE_SMALL);
            }
        }).start();

        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * de Goma de borrar
     */
    public static void useEraser(canvas c) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                canvas.Ruler = false;
                canvas.HandMade = false;
                canvas.Compass = false;
                canvas.Eraser = true;


                canvas.compasLayer = false;
                canvas.compassBmp = null;
                canvas.compassT1 = false;
                canvas.compassT2 = false;
                canvas.compassT3 = false;
                canvas.circleFixed = false;
                canvas.drawing = true;
                canvas.cnv = new Canvas(canvas.bmp);

                canvas.rulerLayer = false;
                canvas.rulerBmp = null;
                canvas.rulerT1 = false;
                canvas.rulerT2 = false;
                canvas.accepted = false;

                canvas.cnv = new Canvas(canvas.bmp);

                canvas.p.setColor(0xFFFFFFFF);
                canvas.p.setStrokeWidth(canvas.SIZE_SMALL);
            }
        }).start();

        c.invalidate();
    }


}