package com.draw_lessons.app.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.activity_draw;
import com.draw_lessons.app.contenidos.activity_contenidos;


// Pantalla del menú principal desde donde se puede escoger entre
// abrir nuevo proyecto, recuperar uno existente, entrar en la sección de activity_contenidos
// o salir de la aplicación.

public class activity_homescreen extends ActionBarActivity implements View.OnClickListener {

    ImageButton bt_exit;
    ImageButton bt_new;
    ImageButton bt_open;
    ImageButton bt_content;
    Intent i_draw;
    Intent i_content;

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

        i_draw = new Intent(this, activity_draw.class);
        i_content = new Intent(this, activity_contenidos.class);
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

        switch (option) {

            case R.id.imageView_new:
                startActivity(i_draw);
                finish();
                break;
            case R.id.imageView_open:
                break;
            case R.id.imageView_contents:
                startActivity(i_content);
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
}
