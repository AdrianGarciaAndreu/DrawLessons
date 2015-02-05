package com.draw_lessons.app.contenidos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.draw_lessons.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment_temas extends ActionBarActivity {

    RecyclerView.Adapter rvAdapter;
    List<adapter_tema.Section> sections = new ArrayList<adapter_tema.Section>();
    private RecyclerView rv_temas;
    private ProgressDialog pDialog;
    private JSONArray json_contenidos = null;
    private JSONArray json_temas = null;
    private ArrayList<item_contenido> contenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_temas);

        rv_temas = (RecyclerView) findViewById(R.id.recyler_view_temas);
        rv_temas.setHasFixedSize(true);

        rv_temas.setLayoutManager(new LinearLayoutManager(this));

        //rv_temas.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        cargarTemas ct = new cargarTemas(this);
        ct.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private class cargarTemas extends AsyncTask<Void, Void, Void> {

        private Context context;

        public cargarTemas(Context context) {
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

            contenidos = new ArrayList<item_contenido>();
            //Creando instancia de la clase de webservice
            webservice wb = new webservice();

            //Guarda el string del JSON
            String jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=getTemasCurso&id=1");

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    //Recogiendo el nodo del array JSON
                    json_temas = jsonObj.getJSONArray("Datos");

                    int p = 0;
                    //Recorriendo todos los objetos del JSON
                    for (int i = 0; i < json_temas.length(); i++) {
                        JSONObject c = json_temas.getJSONObject(i);

                        int id_tema = c.getInt("id_tema");
                        String nombre_tema = c.getString("nombre_tema");
                        sections.add(new adapter_tema.Section(p, nombre_tema));
                        String consulta = "http://draw-lessons.com/api/?a=getContenidosTema&id=";
                        consulta += id_tema;
                        jsonString = wb.makeServiceCall(consulta);

                        JSONObject jsonObj2 = new JSONObject(jsonString);
                        json_contenidos = jsonObj2.getJSONArray("Datos");
                        for (int j = 0; j < json_contenidos.length(); j++) {
                            JSONObject d = json_contenidos.getJSONObject(j);

                            int id_contenido = d.getInt("id_contenido");

                            String nombre_contenido = d.getString("nombre_contenido");
                            String texto_contenido = d.getString("texto_contenido");
                            String imagen_contenido = d.getString("imagen_contenido");
                            String video_contenido = d.getString("video_contenido");
                            String enlace_contenido = d.getString("enlace_contenido");
                            int duracion_contenido = d.getInt("duracion_contenido");

                            item_contenido item = new item_contenido(id_contenido, nombre_contenido, texto_contenido, imagen_contenido, video_contenido, enlace_contenido, duracion_contenido);
                            contenidos.add(item);
                        }
                        p += json_contenidos.length();
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

            rvAdapter = new adapter_contenido(contenidos);

            //Add your adapter to the sectionAdapter
            adapter_tema.Section[] dummy = new adapter_tema.Section[sections.size()];
            adapter_tema mSectionedAdapter = new adapter_tema(context, R.layout.item_tema, R.id.section_text, rvAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            //Apply this adapter to the RecyclerView
            rv_temas.setAdapter(mSectionedAdapter);
        }
    }

}