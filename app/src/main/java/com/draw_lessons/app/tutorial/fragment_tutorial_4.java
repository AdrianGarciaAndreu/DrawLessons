package com.draw_lessons.app.tutorial;

/**
 * Created by Emilio on 02/02/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.draw_lessons.app.menus.activity_homescreen;
import com.draw_lessons.app.R;


// Última pantalla del activity_tutorial de bienvenida donde se realiza el login de Google.
public class fragment_tutorial_4 extends Fragment implements View.OnClickListener {

    Button google;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tutorial_4, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        google = (Button) getView().findViewById(R.id.bt_google);

        google.setOnClickListener(this);
    }


    //Lo que hace el botón de registro de Google
    @Override
    public void onClick(View v) {

        if (v == google) {

            // Si el usuario ha pulsado el botón, guardamos el dato.
            this.saveSingInGoogle();
            // Pasamos a la pantalla principal.
            Intent intent = new Intent(getActivity(), activity_homescreen.class);
            startActivity(intent);
            //para que una vez en la pantalla principal no vuelva al activity_tutorial.
            getActivity().finish();
        }


    }

    // Este método guarda si el usuario se ha logeado para no volver a mostrar el activity_tutorial.
    public void saveSingInGoogle() {
        Context context = getActivity();
        SharedPreferences preferences;
        preferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt("singIn", 1);
        editor.commit();
    }
}

