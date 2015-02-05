package com.draw_lessons.app.canvas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Adrian on 30/01/2015.
 */
public class saver {

    private String path;
    public static Bitmap b;
    private Context c;

    public saver(Bitmap b,Context c,String name) {

        this.path = Environment.getExternalStorageDirectory().toString() + "/DrawLessons/"+name+".png";
        this.b = b;
        this.c = c;

    }


    public void Save() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(path);
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    saver.b.compress(Bitmap.CompressFormat.PNG, 100, fos);


                    //Intent para pasarle a la galería el archivo guardado en forma de URI
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    c.sendBroadcast(mediaScanIntent);

                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();

    }




}
