package com.example.ausias.intercibus.recyclers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.classes.Extra;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class RecyclerCrearEspai extends RecyclerView.Adapter <RecyclerCrearEspai.ViewHolder> {

    public List<Extra> extres;

    /**
     * Constructor del adaptador
     * @param extres Paràmetre que fa referència a una llista de extres
     */
    public RecyclerCrearEspai(List<Extra> extres) {

        this.extres = extres;
    }

    @Override
    public RecyclerCrearEspai.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fila_afegeix, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerCrearEspai.ViewHolder holder, int position) {

        String nom = extres.get(position).getNom();
        String tempMin = Float.toString(extres.get(position).getPreu());

        holder.tvNom.setText(nom);
        holder.tvPreu.setText(tempMin);
    }

    @Override
    public int getItemCount() {
        return extres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNom;
        TextView tvPreu;
        Button btEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.nomFila);
            tvPreu = itemView.findViewById(R.id.preuFila);
            btEliminar = itemView.findViewById(R.id.botoEliminar);

            btEliminar.setOnClickListener(this);
        }

        /**
         * Mètode que serveix per eliminar un extra de la llista
         * @param v Parèmetre que fa referència a una vista
         */
        @Override
        public void onClick(View v) {
            int number = this.getAdapterPosition();
            Log.d("Eliminar", number + "");
            extres.remove(number);

            notifyDataSetChanged();
        }
    }
}