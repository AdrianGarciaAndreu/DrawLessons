package com.draw_lessons.app.contenidos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.draw_lessons.app.R;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class fragment_cursos extends Fragment implements MaterialTabListener {
    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_cursos, container, false);

        tabHost = (MaterialTabHost) rootView.findViewById(R.id.tabHost_cursos);
        pager = (ViewPager) rootView.findViewById(R.id.pager_cursos);


        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        Fragment propios = fragment_cursos_pestana.newInstance("Propios");
        Fragment favoritos = fragment_cursos_pestana.newInstance("Favoritos");
        Fragment todos = fragment_cursos_pestana.newInstance("Todos");
        adapter.addFragment(propios);
        adapter.addFragment(favoritos);
        adapter.addFragment(todos);


        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        tabHost.addTab(tabHost.newTab().setText("Mis cursos").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("Favoritos").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("Todos los curos").setTabListener(this));

        return rootView;
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<Fragment>();
        }

        public void addFragment(Fragment fragment) {
            this.fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int arg0) {
            return this.fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}