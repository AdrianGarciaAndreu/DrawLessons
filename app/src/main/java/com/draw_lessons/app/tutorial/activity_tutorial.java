package com.draw_lessons.app.tutorial;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.draw_lessons.app.R;

import java.util.ArrayList;
import java.util.List;

// Esta es la activity principal de los fragments del activity_tutorial.

public class activity_tutorial extends FragmentActivity {

    adapter_tutorial mPagerAdapter;
    ViewPager mviewPager;
    private List<Fragment> listaFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tutorial);
//        getActionBar().hide();


        // Creamos una lista de Fragments
        listaFragments = new ArrayList<Fragment>();
        listaFragments.add(new fragment_tutorial_1());
        listaFragments.add(new fragment_tutorial_2());
        listaFragments.add(new fragment_tutorial_3());
        listaFragments.add(new fragment_tutorial_4());

        // Creamos nuestro Adapter
        mPagerAdapter = new adapter_tutorial(getSupportFragmentManager(),
                listaFragments);

        // Instanciamos nuestro ViewPager
        mviewPager = (ViewPager) findViewById(R.id.viewPager);

        // Establecemos el Adapter
        mviewPager.setAdapter(mPagerAdapter);
    }
}
