package com.draw_lessons.app.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.draw_lessons.app.R;


// Primera pantalla del activity_tutorial de bienvenida.
public class fragment_tutorial_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tutorial_1, container, false);
    }

}