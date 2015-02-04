package com.draw_lessons.app.tutorial;

/**
 * Created by Emilio on 02/02/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.draw_lessons.app.R;

// tercera pantalla del activity_tutorial de bienvenida.
public class fragment_tutorial_3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tutorial_3, container, false);
    }

}
