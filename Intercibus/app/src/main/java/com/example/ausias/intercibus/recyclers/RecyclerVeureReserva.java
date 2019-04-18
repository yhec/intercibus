package com.example.ausias.intercibus.recyclers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.classes.Extra;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class RecyclerVeureReserva extends RecyclerView.Adapter <RecyclerVeureReserva.ViewHolder> {

    public List<Extra> serveis;

    /**
     * Constructor del adaptador
     * @param serveis Paràmetre que fa referència a una llista de serveis
     */
    public RecyclerVeureReserva(List<Extra> serveis) {

        this.serveis = serveis;
    }

    @Override
    public RecyclerVeureReserva.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fila_mostra, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerVeureReserva.ViewHolder holder, int position) {

        String nom = serveis.get(position).getNom();
        // Agafar el preu sense decimals si valen 0
        float preuFloat = serveis.get(position).getPreu();

        String preu;
        if (preuFloat % 1 == 0) {
            preu = String.valueOf((int)preuFloat);
        }
        else {
            preu = String.valueOf(preuFloat);
        }

        holder.tvNom.setText(nom);
        holder.tvPreu.setText(preu + " €");
    }

    @Override
    public int getItemCount() {

        return serveis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNom;
        TextView tvPreu;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.nomFila);
            tvPreu = itemView.findViewById(R.id.preuFila);
        }
    }
}

