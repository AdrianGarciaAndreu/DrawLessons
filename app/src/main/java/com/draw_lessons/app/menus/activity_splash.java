
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
// En esta clase también se recupera el dato para saber si el user ha ojeado
// el tutorial y decidir si mostramos o no el activity_tutorial cuando vuelva a entrar en la app.

public class activity_splash extends Activity {

    // Tiempo de duración de la splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    // si vale false se mostrará el tutorial de inicio. Si vale true saltará del splash a la pantalla principal.
    private boolean showTutorial = false;

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

                if (!showTutorial) {// Si el user no ha leido el tuto va al activity_tutorial.

                    Intent mainIntent = new Intent().setClass(
                            activity_splash.this, activity_tutorial.class);
                    startActivity(mainIntent);

                } else {// Si el user todavía no ha leido el tuto, salta al menú principal.

                    Intent mainIntent = new Intent().setClass(activity_splash.this, activity_login.class);
                    //
                    //
                    //
                    //Intent mainIntent = new Intent().setClass(activity_splash.this, activity_homescreen.class);
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


    // Comprueba si el usuario ha leido el tutorial de inicio para no volver a mostrar
    // el activity_tutorial. El método da valor a la variable isSignIn. 0 = no leido y 1 = leido.
    public void loadSignIn() {
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        showTutorial = preferences.getBoolean("showTuto", false);
    }
}
