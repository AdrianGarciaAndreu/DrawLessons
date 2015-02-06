package com.draw_lessons.app.contenidos;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.draw_lessons.app.R;
import com.draw_lessons.app.canvas.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class fragment_cursos_pestana extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    //Progress Dialog para la carga de los cursos
    private ProgressDialog pDialog;

    //ArrayList de objetos item_curso
    private ArrayList<item_curso> cursos = new ArrayList<item_curso>();

    //JSONArray Cursos
    private JSONArray json_cursos = null;

    private String consulta;

    private OnCursoSeleccionadoListener listener;

    private int id_usuario;

    public fragment_cursos_pestana() {
        // Required empty public constructor
    }

    private View rootView;

    public static fragment_cursos_pestana newInstance(String pestaña) {

        // Instantiate a new fragment
        fragment_cursos_pestana fragment = new fragment_cursos_pestana();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putString("Pestaña", pestaña);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String pestaña = getArguments().getString("Pestaña");
        id_usuario = getActivity().getIntent().getIntExtra("personID", 0);

        if (pestaña.equals("Propios")) {
            consulta = "http://draw-lessons.com/api/?a=getCursosPropios&id=" + id_usuario;


        } else if (pestaña.equals("Favoritos")) {
            consulta = "http://draw-lessons.com/api/?a=getCursosFavoritos&id=" + id_usuario;
            Log.e("YEE", consulta);

        } else if (pestaña.equals("Todos")) {
            consulta = "http://draw-lessons.com/api/?a=getListadoCursos";

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cursos_pestana, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(rootView.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        int id = cursos.get(position).getId();
                        String nom = cursos.get(position).getNombre();
                        String desc = cursos.get(position).getDescripcion();
                        String img = cursos.get(position).getImagen();
                        listener.onCursoSeleccionado(id, nom, desc, img);


                    }
                })
        );

        new cargarCursos().execute();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnCursoSeleccionadoListener) activity;
        } catch (ClassCastException e) {
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class cargarCursos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(rootView.getContext());
            pDialog.setMessage("Cargando...");
            //pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Creando instancia de la clase de webservice
            webservice wb = new webservice();

            //Guarda el string del JSON
            String jsonString = wb.makeServiceCall(consulta);

            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    //Recogiendo el nodo del array JSON
                    json_cursos = jsonObj.getJSONArray("Datos");

                    cursos.clear();

                    //Recorriendo todos los objetos del JSON
                    for (int i = 0; i < json_cursos.length(); i++) {
                        JSONObject c = json_cursos.getJSONObject(i);

                        int id_curso = c.getInt("id_curso");
                        String nombre_curso = c.getString("nombre_curso");
                        String descripcion_curso = c.getString("descripcion_curso");
                        String imagen_curso = c.getString("imagen_curso");
                        int id_propietario_curso = c.getInt("id_propietario_curso");

                        item_curso item = new item_curso(id_curso, nombre_curso, descripcion_curso, imagen_curso, id_propietario_curso);
                        cursos.add(item);
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

            adapter = new adapter_curso(cursos, rootView.getContext());
            recyclerView.setAdapter(adapter);

        }
    }

    //Interfaz comunicación con el acticity
    public interface OnCursoSeleccionadoListener {
        public void onCursoSeleccionado(int id_tema, String nom, String desc, String img);
    }
}