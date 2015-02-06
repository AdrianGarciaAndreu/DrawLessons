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
import android.widget.ImageButton;

import com.draw_lessons.app.R;
import com.draw_lessons.app.menus.activity_homescreen;
import com.draw_lessons.app.menus.activity_login;


// Última pantalla del activity_tutorial de bienvenida donde se realiza el login de Google.
public class fragment_tutorial_4 extends Fragment implements View.OnClickListener {

    ImageButton understood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tutorial_4, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        understood = (ImageButton) getView().findViewById(R.id.ib_understood);

        understood.setOnClickListener(this);
    }




    //Lo que hace el botón de tutorial entendido
    @Override
    public void onClick(View v) {

        if (v == understood) {

            // Si el usuario ha pulsado el botón, guardamos el dato.
            this.saveSingInGoogle();
            // Pasamos a la pantalla principal.
            Intent intent = new Intent(getActivity(), activity_login.class);
            startActivity(intent);

            //para que una vez en la pantalla principal no vuelva al activity_tutorial.
            getActivity().finish();
        }


    }






    // Este método guarda si el usuario ha entendido el tutorial para no volver a mostrar el activity_tutorial.
    public void saveSingInGoogle() {
        Context context = getActivity();
        SharedPreferences preferences;
        preferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt("singIn", 1);
        editor.commit();
    }
}

