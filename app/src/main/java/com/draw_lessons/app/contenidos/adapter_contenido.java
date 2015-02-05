package com.draw_lessons.app.contenidos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.draw_lessons.app.R;

import java.util.ArrayList;

/**
 * Created by javichaques on 2/2/15.
 */
public class adapter_contenido extends RecyclerView.Adapter<adapter_contenido.MyViewHolder> {

    private ArrayList<item_contenido> contenidosDataSet;
    private webservice wb = new webservice();
    private Context context;

    public adapter_contenido(ArrayList<item_contenido> contenidos) {
        this.contenidosDataSet = contenidos;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nombre;
        TextView tv_duracion;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.tv_nombre = (TextView) itemView.findViewById(R.id.tv_nombre);
            this.tv_duracion = (TextView) itemView.findViewById(R.id.tv_duracion);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contenido, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView tv_nombre = holder.tv_nombre;
        TextView tv_duracion = holder.tv_duracion;

        tv_nombre.setText(contenidosDataSet.get(listPosition).getNombre_contenido());
        tv_duracion.setText(contenidosDataSet.get(listPosition).getDuracion_contenido() + "");
    }

    @Override
    public int getItemCount() {
        return contenidosDataSet.size();
    }
}