package com.draw_lessons.app.canvas;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.draw_lessons.app.R;
import com.draw_lessons.app.contenidos.activity_contenidos;

import java.io.File;

public class activity_draw extends ActionBarActivity {

    private LinearLayout l1;
    private canvas canvas;
    private MenuItem items[];
    private int AppColor = 0x5500AAEE; //color básico de la aplicacion

    DrawerLayout cnvDrawerLayout;
    String[] cnvDrawerListItems;
    ListView cnvDrawerList;
    ActionBarDrawerToggle cnvDrawerToggle;

    public boolean toolClicked = true;
    public int ClickedID = 0;
    private int doBack = 1;

    private String appPath = Environment.getExternalStorageDirectory().toString() + "/DrawLessons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);



        cnvDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        cnvDrawerList = (ListView) findViewById(android.R.id.list);
        cnvDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_cnv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cnv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        cnvDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cnvDrawerListItems));
        cnvDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int editedPosition = position + 1;
                FragmentTransaction ft;
                switch (position) {
                    case 0:
                        //No hacemos nada ya qu estamos en la sección actual
                        break;
                    case 1:
                        Intent i_curso = new Intent(view.getContext(), activity_contenidos.class);
                        startActivity(i_curso);
                        finish();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }

                cnvDrawerLayout.closeDrawer(cnvDrawerList);
            }
        });
        cnvDrawerToggle = new ActionBarDrawerToggle(this,
                cnvDrawerLayout,
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
        cnvDrawerLayout.setDrawerListener(cnvDrawerToggle);

        cnvDrawerToggle.syncState();

        this.createDrawer();
//        this.ToolbarCustom();

        this.items = new MenuItem[6];
        this.prepareFolders();

    }


    NotificationCompat.Builder nb;


    public void prepareFolders() {

        nb = new NotificationCompat.Builder(this);

        new Thread(new Runnable() {

            @Override
            public void run() {

                File f = new File(appPath);
                if (!f.exists()) {
                    f.mkdirs();

                    nb.setSmallIcon(R.drawable.ic_launcher);
                    nb.setContentTitle("Directorio correcto");
                    nb.setContentText("Directorio para DrawLessons creado correctamente.");
                    Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    NotificationManager nmc = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    nmc.notify(1, nb.build());


                }

            }
        }).start();

    }


    /**
     * Método que comprueba la versión del S.O
     * y si es superior a las versiones más bajas
     * aumenta la calidad del dibujo.
     *
     * @param c
     */
    public void getVersion(canvas c) {
        int v = Integer.valueOf(Build.VERSION.SDK_INT);
        if (v >= 17) {
            //si la version de android es inferior a el nivel de API 17, se reduce la calidad
            // del dibujo.
            c.ImproveQuality();
        }


    }

    /**
     * Método que capta la resolucion de la pantalla, coge el Layout del activity, crea un Objeto
     * de la clase Cnv, le da una resolucion X y una resolucion Y al canvas, lanza el método que
     * prepara el Objeto de Cnv, para poder dibujar, y le da un Tamaño al Objeto Paint o pincel.
     * Agrega al layout recogido, el Objeto de tipo Cnv a forma de Objeto View
     */
    public void createDrawer() {

        int x = this.getWindowManager().getDefaultDisplay().getWidth(); //resolucion del ancho de la pantalla
        int y = this.getWindowManager().getDefaultDisplay().getHeight(); // resolucion del alto de la pantalla

        this.l1 = (LinearLayout) this.findViewById(R.id.LinearLCnv1);


        this.canvas = new canvas(this);

        this.canvas.setResX(x);
        this.canvas.setResY(y);
        this.canvas.prepareCancas();
        this.canvas.setStrokeSize(canvas.SIZE_SMALL);
        // this.canvas.setRubishIcon(R.drawable.rubish);


        this.l1.addView(canvas);


    }


/*
    */
/**
 * Metodo para gestionar
 * la personalización de la barra de
 * tareas deprecated
 *//*

    public void ToolbarCustom(){
        android.support.v7.app.ActionBar ab = this.getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(0x5500AAEE));

        ab.setHomeButtonEnabled(true);
        ab.setLogo(R.drawable.icondl);

        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.icondl);

    }
*/


    /**
     * Método para manjera los menus
     * de la ActionBar del activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.addMenuActions(menu);

        return true;
    }


    /**
     * Metodo para manjera los eventos de los
     * menus del ActionBar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.addMenuItems(item);
        return super.onOptionsItemSelected(item);
    }


    /**
     * Oculta todos los elementos del menu
     * menos el indicado por parametro
     *
     * @param h
     */
    public void hide(int h) {

        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] != null) {
                if (this.items[i].getItemId() != h) {
                    this.items[i].setVisible(false);
                }
            }
        }
    }


    /**
     * Des-Oculta todos los elemenos
     * del menu
     */
    public void UnHide() {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] != null) {
                this.items[i].setVisible(true);
            }
        }
    }


    public static MenuItem i1;
    public static MenuItem i2;

    /**
     * Metodo para añadir menus a la
     * Barra de acion del activity
     *
     * @param menu
     */
    public void addMenuActions(Menu menu) {

        this.items[0] = menu.add(0, 0, menu.NONE, "Mano alzada");
        this.items[0].setIcon(R.drawable.ic_pencil);
        this.items[0].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        this.items[1] = menu.add(0, 1, menu.NONE, "Regla Recta");
        this.items[1].setIcon(R.drawable.ic_ruler);
        this.items[1].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[1].setVisible(false);

        this.items[2] = menu.add(0, 2, menu.NONE, "Borrado");
        this.items[2].setIcon(R.drawable.ic_eraser);
        this.items[2].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[2].setVisible(false);

        this.items[3] = menu.add(0, 4, menu.NONE, "Compas");
        this.items[3].setIcon(R.drawable.ic_compass);
        this.items[3].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[3].setVisible(false);


        this.i1 = menu.add(0, 7, Menu.NONE, "Aceptar");
        i1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        i1.setIcon(R.drawable.ic_ok);
        i1.setVisible(false);

        this.i2 = menu.add(0, 8, Menu.NONE, "Rechazar");
        i2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        i2.setIcon(R.drawable.ic_cancel);
        i2.setVisible(false);

        menu.add(0, 3, menu.NONE, "Limpiar").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 5, menu.NONE, "Guardar").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 6, Menu.NONE, "Deshacer").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


    }


    /**
     * Metodo para añadir acciones de control a los items
     * de los Menus de la actionBar
     *
     * @param item
     */
    public void addMenuItems(MenuItem item) {
        int id = item.getItemId();

        if (id == 2) {
            tools.useEraser(this.canvas);
            this.ClickedID = id;
        }
        if (id == 1) {
            tools.useRuler(this.canvas);
            this.ClickedID = id;
        }
        if (id == 0) {
            tools.useHand(this.canvas);
            this.ClickedID = id;
        }
        if (id == 4) {
            tools.useCompass(this.canvas);
            this.ClickedID = id;
        }

        if (id != 3
                && id != 5
                && id != 6
                && id != 7
                && id != 8) {

            if (this.toolClicked == true) {
                this.UnHide();
                this.toolClicked = false;
            } else {
                this.hide(id);
                this.toolClicked = true;
            }
        }

        if (id == 3) {

            cleaner c = new cleaner(this, this.canvas);
            c.cleanCanvas();
        }


        if (id == 5) {
            saver s = new saver(canvas.getBitmapt());
            s.Save();
            Toast.makeText(getBaseContext(), "Saved Image", Toast.LENGTH_SHORT).show();
        }


        if (item.getItemId() == 6) {
            this.canvas.Undo();
        }


        //Aceptar o rechazar Trazo
        if (item.getItemId() == 7) {

            if (ClickedID == 4) {
                if (!this.canvas.getCircleFixed()) {
                    this.canvas.acceptCompassRadius();
                } else {
                    this.canvas.acceptCompas();
                }
            } else {
                this.canvas.acceptDraw();
            }
        }
        if (item.getItemId() == 8) {

            if (ClickedID == 4) {
                if (!this.canvas.getCircleFixed()) {
                    this.canvas.dismissCompassRadius();
                } else {
                    this.canvas.dismissCompass();
                }
            } else {
                this.canvas.dismissDraw();
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.canvas.savePaths();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.canvas.restorePaths();
    }


}
