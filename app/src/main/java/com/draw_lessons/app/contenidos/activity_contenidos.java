package com.draw_lessons.app.contenidos;

/**
 * Created by Aleix on 2015-02-01.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.RecyclerItemClickListener;
import com.draw_lessons.app.canvas.activity_draw;
import com.draw_lessons.app.canvas.cnvAdapter;

public class activity_contenidos extends ActionBarActivity {

    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    RecyclerView.Adapter contAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager contLayoutManager;
    RecyclerView contRecycle;

    String NAME = "Aleix Casanova";
    String EMAIL = "aleix.casanova@gmail.com";
    int PROFILE = R.drawable.icondl;
    String TITLES[] = {"Canvas","Contenidos"};
    int ICONS[] = {R.drawable.icondl, R.drawable.icondl, R.drawable.icondl};

    FragmentManager fm;
    Fragment frag;
    FragmentTransaction ft;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        contRecycle = (RecyclerView) findViewById(R.id.recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        contRecycle.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        contAdapter= new com.draw_lessons.app.canvas.cnvAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);

        contRecycle.setAdapter(contAdapter);
        contLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        contRecycle.setLayoutManager(contLayoutManager);
        contRecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        switch (position) {
                            case 0:
                                //Header no hacer nada
                                break;
                            case 1:
                                Intent i_canvas = new Intent(view.getContext(), activity_draw.class);
                                startActivity(i_canvas);
                                finish();
                                break;
                            case 2:
                                //No hacemos nada ya qu estamos en la sección actual
                                break;
                            case 3:

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
}
