package com.draw_lessons.app.menus;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.activity_draw;
import com.draw_lessons.app.contenidos.activity_contenidos;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

// Pantalla del menú principal desde donde se puede escoger entre
// abrir nuevo proyecto, recuperar uno existente, entrar en la sección de activity_contenidos
// o salir de la aplicación.

public class activity_homescreen extends ActionBarActivity implements View.OnClickListener {

    ImageButton bt_exit;
    ImageButton bt_new;
    ImageButton bt_open;
    ImageButton bt_content;
    Intent i_principal;

    CircleImageView img_user;
    TextView tv_nombre;
    TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_homescreen);

        bt_exit = (ImageButton) findViewById(R.id.imageView_exit);
        bt_new = (ImageButton) findViewById(R.id.imageView_new);
        bt_open = (ImageButton) findViewById(R.id.imageView_open);
        bt_content = (ImageButton) findViewById(R.id.imageView_contents);
        bt_exit.setOnClickListener(this);
        bt_new.setOnClickListener(this);
        bt_open.setOnClickListener(this);
        bt_content.setOnClickListener(this);
        i_principal = getIntent();
        img_user = (CircleImageView) findViewById(R.id.img_user);
        tv_nombre = (TextView) findViewById(R.id.tv_nameuser);
        tv_email = (TextView) findViewById(R.id.tv_emailuser);

        cargarDatosUsuario();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int option = v.getId();
        Intent i;
        switch (option) {
            case R.id.imageView_new:
                i = new Intent(this, activity_draw.class);
                i.putExtras(i_principal);
                startActivity(i);
                finish();
                break;
            case R.id.imageView_open:
                i = new Intent();
                i.putExtras(i_principal);
                //i.setType("image/*");
               // i.setAction(Intent.ACTION_GET_CONTENT);
               // startActivityForResult(Intent.createChooser(i, "Escoge imágen"), 1);


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

                break;
            case R.id.imageView_contents:
                i = new Intent(this, activity_contenidos.class);
                i.putExtras(i_principal);
                startActivity(i);
                finish();
                break;
            case R.id.imageView_exit:
                finish();
                break;

        }


//        if(v == bt_exit){
//            finish();
//        }
    }


    /**
     * Recibiendo resultado de
     * la apertura de un proyecto
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri u = data.getData();
            Cursor cursor;

            if (Build.VERSION.SDK_INT >= 19) {
                 int takeFlags = data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

              getContentResolver().takePersistableUriPermission(u, takeFlags);
            }

            // Toast.makeText(this, u.toString(), Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            String s = "open";
            i.putExtra("open", s);
            String path;

            String[] proj = {MediaStore.Images.Media.};
            cursor = getContentResolver().query(u, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();

            String[] a = path.split("t");

            i.putExtra("file", path);
            i.setClass(activity_homescreen.this, activity_draw.class);
            i.putExtras(getIntent());
            startActivity(i);


            finish();


        }

    }

    private void cargarDatosUsuario() {
        tv_email.setText(getIntent().getStringExtra("personEmail"));
        tv_nombre.setText(getIntent().getStringExtra("personName"));
        Picasso.with(this).load(getIntent().getStringExtra("personPhotoUrl")).placeholder(R.drawable.user_photo)
                .error(R.drawable.user_photo).fit().into(img_user);
    }


}
