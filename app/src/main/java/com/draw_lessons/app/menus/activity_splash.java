package com.draw_lessons.app.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.draw_lessons.app.R;
import com.draw_lessons.app.tutorial.activity_tutorial;

import java.util.Timer;
import java.util.TimerTask;


// Esta es la pantalla de Splash.
// En esta clase también se recupera el dato para saber si el user se ha logeado
// con anterioridad y decidir si mostramos o no el activity_tutorial.

public class activity_splash extends Activity {

    // Tiempo de duración de la splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    // si vale 0 el user no está logeado. Si vale 1 sí lo está.
    private int isSignIn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        this.loadSignIn();


        //Inicia nuevo hilo
        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                // Inicia el siguiente activity

                if (isSignIn == 0) {// Si el user no está logeado va al activity_tutorial.

                    Intent mainIntent = new Intent().setClass(
                            activity_splash.this, activity_tutorial.class);
                    startActivity(mainIntent);

                } else {// Si el user está logeado salta al menú principal.

                    Intent mainIntent = new Intent().setClass(activity_splash.this, activity_login.class);
                    startActivity(mainIntent);
                    finish();
                }


                // Cierra la activity e impide que el usuario pueda volver a ella
                // al presionar el botón de volver atrás.
                finish();
            }
        };

        // Simula el proceso de carga cuando arranca la aplicación.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


    // Comprueba si el usuario se ha logeado para no volver a mostrar el activity_tutorial.
    // El método da valor a la variable isSignIn. 0 = no logeado y 1 = logeado.
    public void loadSignIn() {
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        isSignIn = preferences.getInt("singIn", 0);
    }
}
