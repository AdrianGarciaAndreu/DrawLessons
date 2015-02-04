package com.draw_lessons.app.canvas;


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
    public static void useRuler(cnv c) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cnv.Compass = false;
                cnv.HandMade = false;
                cnv.Eraser = false;
                cnv.Ruler = true;

                cnv.compasLayer = false;
                cnv.compassBmp = null;
                cnv.compassT1 = false;
                cnv.compassT2 = false;

                cnv.p.setColor(0xFF000000);
                cnv.p.setStrokeWidth(cnv.SIZE_SMALL);


            }
        }).start();
        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * del compas
     */
    public static void useCompass(cnv c) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                cnv.Ruler = false;
                cnv.HandMade = false;
                cnv.Compass = true;
                cnv.Eraser = false;

                cnv.rulerLayer = false;
                cnv.rulerBmp = null;
                cnv.rulerT1 = false;
                cnv.rulerT2 = false;

                cnv.p.setColor(0xFF000000);
                cnv.p.setStrokeWidth(cnv.SIZE_SMALL);

            }
        }).start();


        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * de Mano alzada
     */
    public static void useHand(cnv c) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                cnv.Ruler = false;
                cnv.HandMade = true;
                cnv.Compass = false;
                cnv.Eraser = false;

                cnv.compasLayer = false;
                cnv.compassBmp = null;
                cnv.compassT1 = false;
                cnv.compassT2 = false;

                cnv.rulerLayer = false;
                cnv.rulerBmp = null;
                cnv.rulerT1 = false;
                cnv.rulerT2 = false;

                cnv.p.setColor(0xFF000000);
                cnv.p.setStrokeWidth(cnv.SIZE_SMALL);
            }
        }).start();

        c.invalidate();
    }


    /**
     * Método para elegir la herramienta
     * de Goma de borrar
     */
    public static void useEraser(cnv c) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                cnv.Ruler = false;
                cnv.HandMade = false;
                cnv.Compass = false;
                cnv.Eraser = true;


                cnv.compasLayer = false;
                cnv.compassBmp = null;
                cnv.compassT1 = false;
                cnv.compassT2 = false;

                cnv.rulerLayer = false;
                cnv.rulerBmp = null;
                cnv.rulerT1 = false;
                cnv.rulerT2 = false;

                cnv.p.setColor(0xFFFFFFFF);
                cnv.p.setStrokeWidth(cnv.SIZE_SMALL);
            }
        }).start();

        c.invalidate();
    }


}
