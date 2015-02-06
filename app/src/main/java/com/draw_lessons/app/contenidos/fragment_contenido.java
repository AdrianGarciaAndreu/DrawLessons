package com.draw_lessons.app.contenidos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.draw_lessons.app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class fragment_contenido extends Fragment {

    private int id_contenido = 4;
    ProgressDialog pDialog;

    private View rootView;

    TextView tv_nombre;
    TextView tv_duracion;
    TextView tv_url;
    TextView tv_texto;
    ImageView img_contenido;
    ImageView img_video;
    TextView tv_video;

    public static fragment_contenido newInstance(String nom, int duracion, String url, String texto, String img, String video) {
        fragment_contenido f = new fragment_contenido();

        Bundle args = new Bundle();
        args.putString("nombre_contenido", nom);
        args.putInt("duracion_contenido", duracion);
        args.putString("url_contenido", url);
        args.putString("texto_contenido", texto);
        args.putString("img_contenido", img);
        args.putString("video_contenido", video);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contenido, container, false);

        tv_nombre = (TextView) rootView.findViewById(R.id.tv_nombre_contenido);
        tv_duracion = (TextView) rootView.findViewById(R.id.tv_duracion_contenido);
        tv_url = (TextView) rootView.findViewById(R.id.tv_url_contenido);
        tv_texto = (TextView) rootView.findViewById(R.id.tv_texto_contenido);
        tv_video = (TextView) rootView.findViewById(R.id.tv_nombre_video_contenido);
        img_contenido = (ImageView) rootView.findViewById(R.id.img_contenido);
        img_video = (ImageView) rootView.findViewById(R.id.img_video_contenido);

        Bundle b = getArguments();

        tv_nombre.setText(b.getString("nombre_contenido"));
        tv_duracion.setText(String.valueOf(b.getInt("duracion_contenido")));
        tv_url.setText(b.getString("url_contenido"));
        tv_texto.setText(b.getString("texto_contenido"));
        Picasso.with(getActivity()).load(b.getString("img_contenido")).placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder).fit().into(img_contenido);

        //cargarContenido cc = new cargarContenido(getActivity());
        //cc.execute();

        return rootView;
    }

    private class cargarContenido extends AsyncTask<Void, Void, Void> {

        private Context context;
        String nombre_contenido;
        String texto_contenido;
        String imagen_contenido;
        String video_contenido;
        String enlace_contenido;
        int duracion_contenido;

        public cargarContenido(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //Creando instancia de la clase de webservice
            webservice wb = new webservice();

            //Guarda el string del JSON
            String jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=getContenido&id=" + id_contenido);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    //Recogiendo el nodo del array JSON
                    JSONArray json_contenido = jsonObj.getJSONArray("Datos");

                    //Recorriendo todos los objetos del JSON
                    for (int i = 0; i < json_contenido.length(); i++) {
                        JSONObject c = json_contenido.getJSONObject(i);

                        nombre_contenido = c.getString("nombre_contenido");
                        texto_contenido = c.getString("texto_contenido");
                        imagen_contenido = c.getString("imagen_contenido");
                        video_contenido = c.getString("video_contenido");
                        enlace_contenido = c.getString("enlace_contenido");
                        duracion_contenido = c.getInt("duracion_contenido");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            tv_nombre.setText(nombre_contenido);
            tv_texto.setText(texto_contenido);
            tv_url.setText(enlace_contenido);
            tv_duracion.setText(duracion_contenido + "");
            Picasso.with(context).load(imagen_contenido).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(img_contenido);
        }
    }
}