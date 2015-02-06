package com.draw_lessons.app.contenidos;

/**
 * Created by Aleix on 2015-02-01.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.RecyclerItemClickListener;
import com.draw_lessons.app.canvas.activity_draw;
import com.draw_lessons.app.canvas.cnvAdapter;
import com.draw_lessons.app.menus.activity_homescreen;

public class activity_contenidos extends ActionBarActivity {

    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    RecyclerView.Adapter contAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager contLayoutManager;
    RecyclerView contRecycle;

    Intent intent_principal;
    String NAME;
    String EMAIL;
    String PROFILE;
    String COVER;
    String TITLES[];
    int ICONS[] = {R.drawable.icondl, R.drawable.icondl, R.drawable.icondl, R.drawable.icondl};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenidos);

        intent_principal = getIntent();
        TITLES = getResources().getStringArray(R.array.menu_navigation);
        setDatos(intent_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        contRecycle = (RecyclerView) findViewById(R.id.recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        contRecycle.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size


        contAdapter = new cnvAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, COVER);

        contRecycle.setAdapter(contAdapter);
        contLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        contRecycle.setLayoutManager(contLayoutManager);
        contRecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        switch (position) {
                            case 0:
                                //Header no hacer nada
                                break;
                            case 1:
                                //Menu principal
                                Intent i_hs = new Intent(view.getContext(), activity_homescreen.class);
                                i_hs.putExtras(intent_principal);
                                startActivity(i_hs);
                                finish();
                                break;
                            case 2:
                                Intent i = new Intent(view.getContext(), activity_draw.class);
                                i.putExtras(intent_principal);
                                startActivity(i);
                                finish();
                                break;
                            case 3:
                                //No hacemos nada ya qu estamos en la sección actual
                                break;
                            case 4:
                                finish();
                                break;
                        }

                    }
                })
        );

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        cargarCursos();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, activity_homescreen.class);
        i.putExtras(intent_principal);
        startActivity(i);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(contRecycle)) {
                    mDrawerLayout.closeDrawer(contRecycle);
                } else {
                    mDrawerLayout.openDrawer(contRecycle);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Carga el fragment cursos
    public void cargarCursos() {
        FragmentTransaction ft;
        Fragment frag;
        frag = new fragment_cursos();
        ft = getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag);
        ft.commit();
    }

    private void setDatos(Intent i) {
        NAME = i.getStringExtra("personName");
        EMAIL = i.getStringExtra("personEmail");
        PROFILE = i.getStringExtra("personPhotoUrl");
        COVER = i.getStringExtra("personCoverUrl");
    }
}
