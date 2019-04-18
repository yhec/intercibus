package com.example.ausias.intercibus.recyclers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.classes.Extra;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class RecyclerFerReservaSeleccionarServeis extends RecyclerView.Adapter <RecyclerFerReservaSeleccionarServeis.ViewHolder> {
    public List<Extra> extres;
    public float preuTotal;
    public TextView tvPreuTotal;

    /**
     * Constructor del adaptador
     * @param extres Paràmetre que fa referència a una llista de extres
     * @param preuTotal Paràmetre que fa referència al preu total de la reserva
     * @param tvPreuTotal Paràmetre que fa referència al TextView del preu total
     */
    public RecyclerFerReservaSeleccionarServeis(List<Extra> extres, float preuTotal, TextView tvPreuTotal) {

        this.extres = extres;
        this.preuTotal = preuTotal;
        this.tvPreuTotal = tvPreuTotal;
    }

    @Override
    public RecyclerFerReservaSeleccionarServeis.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fila_reserva_servei, parent, false);

        RecyclerFerReservaSeleccionarServeis.ViewHolder vh = new RecyclerFerReservaSeleccionarServeis.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerFerReservaSeleccionarServeis.ViewHolder holder, int position) {

        Log.d("extres", extres.size()+"");
        Log.d("NOM", extres.get(position).getNom());
        String nom = extres.get(position).getNom();
        // Agafar el preu sense decimals si valen 0
        float preuFloat = extres.get(position).getPreu();
        String preu;
        if (preuFloat % 1 == 0) {
            preu = String.valueOf((int)preuFloat);
        }
        else {
            preu = String.valueOf(preuFloat);
        }

        holder.tvNom.setText(nom);
        holder.tvPreu.setText(preu +  " €");

        //preuTotal += extres.get(position).getPreu();
    }

    @Override
    public int getItemCount() {
        return extres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CheckBox checkbox;
        TextView tvNom;
        TextView tvPreu;

        public ViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkBox);
            tvNom = itemView.findViewById(R.id.tvNomReservaServei);
            tvPreu = itemView.findViewById(R.id.tvPreuReservaServei);
            checkbox.setOnClickListener(this);
        }

        /**
         * Mètode per afegir o eliminar extres a l'hora de crear una reserva
         * @param view Paràmetre que fa referència a una vista
         */
        @Override
        public void onClick(View view) {
            Log.d("CHECKED", checkbox.isChecked()+"");
            int number = this.getAdapterPosition();

            if (checkbox.isChecked()) {
                extres.get(number).setMarcat(true);
                preuTotal += extres.get(number).getPreu();

                if (preuTotal % 1 == 0) {
                    tvPreuTotal.setText(String.valueOf((int)preuTotal) + " €");
                }
                else {
                    tvPreuTotal.setText(String.valueOf(preuTotal) + " €");
                }
            }
            else {
                extres.get(number).setMarcat(false);
                preuTotal -= extres.get(number).getPreu();

                if (preuTotal % 1 == 0) {
                    tvPreuTotal.setText(String.valueOf((int)preuTotal) + " €");
                }
                else {
                    tvPreuTotal.setText(String.valueOf(preuTotal) + " €");
                }
            }
        }
    }
}
