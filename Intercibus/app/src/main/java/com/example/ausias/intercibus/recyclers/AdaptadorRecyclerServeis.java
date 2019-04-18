package com.example.ausias.intercibus.recyclers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.classes.Servei;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Jessica Miranda
 * @version 1.0
 */
public class AdaptadorRecyclerServeis extends RecyclerView.Adapter <AdaptadorRecyclerServeis.ViewHolder> {

    public List<Servei> serveis;
    public List<String> serveisSelect;

    /**
     * Constructor del adaptador
     * @param serveis Paràmetre que fa referència a una llista de serveis
     * @param serveisSelect Paràmetre que fa referència a una llista de serveis seleccionats a la recerca
     */
    public AdaptadorRecyclerServeis(List<Servei> serveis, List<String> serveisSelect) {

        this.serveis = serveis;
        this.serveisSelect = serveisSelect;
    }

    @Override
    public AdaptadorRecyclerServeis.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fila_recerca, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerServeis.ViewHolder holder, int position) {

        String nom = serveis.get(position).getNomServei();
        holder.checkNom.setText(nom);

    }

    @Override
    public int getItemCount() {
        return serveis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CheckBox checkNom;

        public ViewHolder(View itemView) {
            super(itemView);
            checkNom = itemView.findViewById(R.id.checkBoxServei);
            checkNom.setOnClickListener(this);
        }

        /**
         * Mètode que permet afegir o treure serveis a la llista de serveis seleccionats a la recerca
         * @param view Paràmetre que fa referència a una vista
         */
        @Override
        public void onClick(View view) {
            if(checkNom.isChecked()){
                int number = this.getAdapterPosition();
                serveisSelect.add(serveis.get(number).getNomServei());


            }else{
                int number = this.getAdapterPosition();
                String nomServei = serveis.get(number).getNomServei();

                //Busquem el servei seleccionat que volem eliminar de la llista pel nom
                for (int i=0;i<serveisSelect.size();i++) {
                    if(serveisSelect.get(i).equals(nomServei)){
                        serveisSelect.remove(i);
                    }
                }


            }


        }
    }
}