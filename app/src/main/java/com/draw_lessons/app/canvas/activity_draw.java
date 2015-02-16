package com.draw_lessons.app.canvas;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.draw_lessons.app.R;
import com.draw_lessons.app.contenidos.activity_contenidos;
import com.draw_lessons.app.menus.activity_homescreen;

import java.io.File;

public class activity_draw extends ActionBarActivity {

    protected LinearLayout ll1;
    public static canvas canvas;
    private MenuItem items[];

    DrawerLayout cnvDrawerLayout;
    ActionBarDrawerToggle cnvDrawerToggle;

    RecyclerView.Adapter cnvAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager cnvLayoutManager;
    RecyclerView cnvRecycle;

    Intent intent_principal;
    String NAME;
    String EMAIL;
    String PROFILE;
    String COVER;
    String TITLES[];
    int ICONS[] = {R.drawable.ic_home, R.drawable.ic_screendraw, R.drawable.ic_books, R.drawable.ic_error};

    public boolean toolClicked = true;
    public int ClickedID = 0;

    private String appPath = Environment.getExternalStorageDirectory().toString() + "/DrawLessons";
    public EditText et1;

    public static MenuItem i1;
    public static MenuItem i2;

    public static MenuItem distance;
    public static MenuItem x1, y1, x2, y2;

    private String opened=null;
    private String filePath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        intent_principal = getIntent();
        setDatos(intent_principal);
        TITLES = getResources().getStringArray(R.array.menu_navigation);

        cnvRecycle = (RecyclerView) findViewById(R.id.recycler_view_cnv); // Assigning the RecyclerView Object to the xml View
        cnvRecycle.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        cnvAdapter = new cnvAdapter(this, TITLES, ICONS, NAME, EMAIL, PROFILE, COVER);


        cnvDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_cnv);
        cnvRecycle.setAdapter(cnvAdapter);
        cnvLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        cnvRecycle.setLayoutManager(cnvLayoutManager);
        cnvRecycle.addOnItemTouchListener(
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
								CloseDialog();
                                break;
                            case 2:
                                //No hacemos nada ya qu estamos en la sección actual
                                break;
                            case 3:
                                //Contenidos
                                Intent i_curso = new Intent(view.getContext(), activity_contenidos.class);
                                i_curso.putExtras(intent_principal);
                                startActivity(i_curso);
                                finish();
                                break;
                            case 4:
                                finish();
                                break;

                        }

                    }
                })
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cnv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);


        cnvDrawerToggle = new ActionBarDrawerToggle(this,
                cnvDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
//                invalidateOptionsMenu();
                hide(ClickedID);

                syncState();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
//                invalidateOptionsMenu();
                hide(ClickedID);
                syncState();

            }
        };
        cnvDrawerLayout.setDrawerListener(cnvDrawerToggle);
        cnvDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        cnvDrawerToggle.syncState();

         /*
          si el menu abre un archivo
         */
            Intent i = this.getIntent();
                this.opened =  i.getStringExtra("open");
                this.filePath = i.getStringExtra("file");



        this.createDrawer();

        this.items = new MenuItem[6];
        this.prepareFolders();

        this.et1 = new EditText(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        this.et1.setLayoutParams(lp);



    }




    private void setDatos(Intent i) {
        NAME = i.getStringExtra("personName");
        EMAIL = i.getStringExtra("personEmail");
        PROFILE = i.getStringExtra("personPhotoUrl");
        COVER = i.getStringExtra("personCoverUrl");
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

        if (this.opened != null) {
            if (this.opened.equals("open")) {
                canvas.opened = "open";
                File f = new File(this.filePath);
                canvas.openFile = f;
                Toast.makeText(this, "Imagen cargada", Toast.LENGTH_SHORT).show();
            }
        }
        int x = this.getWindowManager().getDefaultDisplay().getWidth(); //resolucion del ancho de la pantalla
        int y = this.getWindowManager().getDefaultDisplay().getHeight(); // resolucion del alto de la pantalla

        this.ll1 = (LinearLayout) this.findViewById(R.id.LinearLCnv1);


        this.canvas = new canvas(this);

        this.canvas.setResX(x);
        this.canvas.setResY(y);
        this.canvas.prepareCanvas();
        this.canvas.setStrokeSize(canvas.SIZE_SMALL);


        this.getVersion(this.canvas);
        this.ll1.addView(canvas);


    }


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
     * oculta todas las herramientas a excepción del Lápiz
     */
    public void resetToolbar() {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] != null) {
                if (this.items[i].getItemId() != 0) {
                    this.items[i].setVisible(false);
                } else {
                    this.items[i].setVisible(true);
                    this.ClickedID = i;
                }
            }
        }
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


    /**
     * Metodo para añadir menus a la
     * Barra de acion del activity
     *
     * @param menu
     */
    public void addMenuActions(Menu menu) {

        this.i1 = menu.add(0, 7, Menu.NONE, R.string.accept);
        i1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        i1.setIcon(this.getResources().getDrawable(R.drawable.ic_ok));
        i1.setVisible(false);

        this.i2 = menu.add(0, 8, Menu.NONE, R.string.dismiss);
        i2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        i2.setIcon(this.getResources().getDrawable(R.drawable.ic_cancel));
        i2.setVisible(false);


        activity_draw.x1 = menu.add(0, 11, menu.NONE, "x1");
        activity_draw.x1.setTitle("0.00, x1");
        activity_draw.x1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        activity_draw.y1 = menu.add(0, 12, menu.NONE, "y1");
        activity_draw.y1.setTitle("0.00, y1");
        activity_draw.y1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        activity_draw.x2 = menu.add(0, 13, menu.NONE, "x2");
        activity_draw.x2.setTitle("0.00, x2");
        activity_draw.x2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        activity_draw.y2 = menu.add(0, 14, menu.NONE, "y2");
        activity_draw.y2.setTitle("0.00, y2");
        activity_draw.y2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        //Distancia del ultimo Objeto despues de ser usado
        activity_draw.distance = menu.add(0, 10, menu.NONE, "Distancia/Radio");
        activity_draw.distance.setTitle("0.00, dist");
        activity_draw.distance.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 11, menu.NONE, R.string.space).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        this.items[0] = menu.add(0, 0, menu.NONE, R.string.hand_made);
        this.items[0].setIcon(R.drawable.ic_pencil);
        this.items[0].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        this.items[1] = menu.add(0, 1, menu.NONE, R.string.ruler);
        this.items[1].setIcon(R.drawable.ic_ruler);
        this.items[1].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[1].setVisible(false);

        this.items[2] = menu.add(0, 2, menu.NONE, R.string.eraser);
        this.items[2].setIcon(R.drawable.ic_eraser);
        this.items[2].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[2].setVisible(false);


        this.items[3] = menu.add(0, 4, menu.NONE, R.string.compass);
        this.items[3].setIcon(R.drawable.compass);
        this.items[3].setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.items[3].setVisible(false);

        // Espacio, entre herramientas
        menu.add(0, 9, menu.NONE, R.string.space).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        MenuItem i3 = menu.add(0, 6, Menu.NONE, R.string.undo);
        i3.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        i3.setIcon(this.getResources().getDrawable(R.drawable.undo));


        menu.add(0, 3, menu.NONE, R.string.clean).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 5, menu.NONE, R.string.save).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);


    }


    /**
     * Metodo para añadir acciones de control a los items
     * de los Menus de la actionBar
     *
     * @param item
     */
    public void addMenuItems(MenuItem item) {
        int id = item.getItemId();
        //Goma de borrar
        if (id == 2) {
            tools.useEraser(this.canvas);
            this.ClickedID = id;
        }
        //regla recta
        if (id == 1) {
            tools.useRuler(this.canvas);
            this.ClickedID = id;
        }
        //Mano alzada
        if (id == 0) {
            tools.useHand(this.canvas);
            this.ClickedID = id;
        }
        //Compás
        if (id == 4) {
            tools.useCompass(this.canvas);
            this.ClickedID = id;
        }

        // id's de Deshacer,Limpiar, Aceptar, Rechazar, Guardar
        if (id != 3
                && id != 5
                && id != 6
                && id != 7
                && id != 8
                && id != 9) {

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
            this.SaveBMP();
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


    /**
     * Crea un dialogo para guardar
     * una imagen
     */
    public void SaveBMP() {

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(R.string.save_msg);
        b.setCancelable(true);

        b.setPositiveButton(R.string.save_draw, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saver s = new saver(canvas.getBitmapt(), getBaseContext(), et1.getText().toString());
                s.Save();
                Toast.makeText(getBaseContext(), R.string.save_toast, Toast.LENGTH_SHORT).show();

            }
        });

        b.setNegativeButton(R.string.cancel, null);


        b.setTitle(R.string.save_title);
        b.setIcon(this.getResources().getDrawable(R.drawable.save));

        b.setView(et1);

        AlertDialog ad = b.create();
        ad.show();
    }


    /**
     * Crea un dialogo para guardar
     * una imagen despues de guardar
     * la imagen, sale al menu
     * <p/>
     * Metodo sobrecargado, el parametro es irrelevante
     */
    public void SaveBMP(boolean ex) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(R.string.save_msg);
        b.setCancelable(true);

        b.setPositiveButton(R.string.save_draw, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saver s = new saver(canvas.getBitmapt(), getBaseContext(), et1.getText().toString());
                s.Save();
                Toast.makeText(getBaseContext(), R.string.save_toast, Toast.LENGTH_SHORT).show();
                Intent i = new Intent().setClass(getBaseContext(), activity_homescreen.class);
                startActivity(i);
                finish();
            }
        });

        b.setNegativeButton(R.string.cancel, null);


        b.setTitle(R.string.save_title);
        b.setIcon(this.getResources().getDrawable(R.drawable.save));

        b.setView(et1);

        AlertDialog ad = b.create();
        ad.show();
    }


    public void CloseDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.exit_title);
        b.setMessage(R.string.exit_msg);
        b.setCancelable(true);

		b.setPositiveButton(R.string.exit_accept, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i_hs = new Intent(getBaseContext(), activity_homescreen.class);

                opened = null;
                filePath = null;

                canvas.setOpened(false);

				i_hs.putExtras(intent_principal);
				startActivity(i_hs);
				finish();
			}
		}).setNegativeButton(R.string.exit_cancel, null);

		b.setNeutralButton(R.string.exit_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveBMP(false);
                opened = null;
                filePath = null;
            }
        });

        b.setIcon(this.getResources().getDrawable(R.drawable.ic_cancel));
        AlertDialog a = b.create();
        a.show();


    }

    @Override
    public void onBackPressed() {
        this.CloseDialog();
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







/*
    Metodos para setear las coordenadas
    de los puntos marcados en el canvas

 */


    public static void setX1(float a) {

        float aux = (float) Math.rint(a * 100) / 100;
        a = aux;
        String s = String.valueOf(a) + ", x1";
        activity_draw.x1.setTitle(s);
    }

    public static void setY1(float a) {

        float aux = (float) Math.rint(a * 100) / 100;
        a = aux;
        String s = String.valueOf(a) + ", y1";
        activity_draw.y1.setTitle(s);
    }

    public static void setX2(float a) {

        float aux = (float) Math.rint(a * 100) / 100;
        a = aux;
        String s = String.valueOf(a) + ", x2";
        activity_draw.x2.setTitle(s);
    }

    public static void setY2(float a) {

        float aux = (float) Math.rint(a * 100) / 100;
        a = aux;
        String s = String.valueOf(a) + ", y2";
        activity_draw.y2.setTitle(s);
    }


    public static canvas getCanvas(){

        return activity_draw.canvas;
    }



}
