package com.draw_lessons.app.contenidos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment_temas extends Fragment {

    RecyclerView.Adapter rvAdapter;
    List<adapter_tema.Section> sections = new ArrayList<adapter_tema.Section>();
    private RecyclerView rv_temas;
    private ProgressDialog pDialog;
    private JSONArray json_contenidos = null;
    private JSONArray json_temas = null;
    private ArrayList<item_contenido> contenidos;
    private int id_curso;
    private String nombre_curso;
    private String descripcion_curso;
    private String img_curso;
    private View rootView;

    private TextView tv_nombre;
    private TextView tv_descripcion;
    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_temas, container, false);
        rv_temas = (RecyclerView) rootView.findViewById(R.id.recyler_view_temas);
        rv_temas.setHasFixedSize(true);
        rv_temas.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_nombre = (TextView) rootView.findViewById(R.id.tv_nombre_curso);
        tv_descripcion = (TextView) rootView.findViewById(R.id.tv_descripcion_curso);
        img = (ImageView) rootView.findViewById(R.id.img_curso);


        id_curso = getArguments().getInt("id_curso");
        nombre_curso = getArguments().getString("nombre_curso");
        descripcion_curso = getArguments().getString("descripcion_curso");
        img_curso = getArguments().getString("img_curso");

        tv_nombre.setText(nombre_curso);
        tv_descripcion.setText(descripcion_curso);
        Picasso.with(getActivity()).load(img_curso).placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder).fit().into(img);

        rv_temas.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(getActivity(),""+position, Toast.LENGTH_SHORT).show();
                    }
                })
        );

        cargarTemas ct = new cargarTemas(getActivity());
        ct.execute();

        return rootView;
    }


    public static fragment_temas newInstance(int id_curso, String nom, String desc, String img) {
        fragment_temas f = new fragment_temas();

        Bundle args = new Bundle();
        args.putInt("id_curso", id_curso);
        args.putString("nombre_curso", nom);
        args.putString("descripcion_curso", desc);
        args.putString("img_curso", img);
        f.setArguments(args);

        return f;
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
            String jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=getTemasCurso&id=" + id_curso);

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