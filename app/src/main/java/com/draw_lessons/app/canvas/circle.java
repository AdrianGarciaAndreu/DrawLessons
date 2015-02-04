package com.draw_lessons.app.canvas;

import android.graphics.Path;

public class circle {

    private Path p;
    private float x1, y1, x2, y2;
    private float r;


    public circle(Path p, float x1, float y1, float x2, float y2, float r) {

        this.p = p;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.r = r;
    }


    public Path getPath() {
        return this.p;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getR() {
        return r;
    }


}
