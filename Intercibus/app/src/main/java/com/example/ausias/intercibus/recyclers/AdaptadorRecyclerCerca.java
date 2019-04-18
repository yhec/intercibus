package com.example.ausias.intercibus.recyclers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.VeureEspai;
import com.example.ausias.intercibus.classes.Espai;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Jessica Miranda
 * @version 1.0
 */
public class AdaptadorRecyclerCerca extends RecyclerView.Adapter <AdaptadorRecyclerCerca.ViewHolder> {

    public List<Espai> espais;

    /**
     * Constructor del adaptador
     * @param espais Paràmetre que fa referència a una llista d'espais
     */
    public AdaptadorRecyclerCerca(List<Espai> espais) {

        this.espais = espais;
    }

    @Override
    public AdaptadorRecyclerCerca.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout. fila_mostra_recerca, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerCerca.ViewHolder holder, int position) {

        Bitmap foto = espais.get(position).getBitmap();
        String nom = espais.get(position).getNom();
        // Volem imprimir la part decimal si es diferent de 0
        String preu;
        float preuFloat = espais.get(position).getPreu();
        if (preuFloat % 1 == 0) {
            preu = String.valueOf((int)preuFloat);
        }
        else {
            preu = String.valueOf(preuFloat);
        }
        //String preu = String.valueOf(espais.get(position).getPreu());
        holder.fotoEspai.setImageBitmap(foto);
        holder.nomEspai.setText(nom);
        holder.preuEspai.setText(preu + " €");
    }

    @Override
    public int getItemCount() {
        return espais.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView fotoEspai;
        TextView nomEspai;
        TextView preuEspai;
        //RelativeLayout layout;
        CardView layout;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoEspai = itemView.findViewById(R.id.espai_foto);
            nomEspai = itemView.findViewById(R.id.espai_nom);
            preuEspai = itemView.findViewById(R.id.espai_preu);

            layout = itemView.findViewById(R.id.cardViewLayout);

            layout.setOnClickListener(this);
        }

        /**
         * Mètode que recull les dades d'una vista i crida a un altre activity passant aquests paràmetres
         * @param view Paràmetre que fa referència a una vista
         */
        @Override
        public void onClick(View view) {

            Log.d("CLICK", "del listener");
            int number = this.getAdapterPosition();

            int id = espais.get(number).getId();
            String nom = espais.get(number).getNom();
            String descripcio = espais.get(number).getDescripcio();
            int area = espais.get(number).getArea();
            String direccio = espais.get(number).getDireccio();
            String provincia = espais.get(number).getProvincia();
            float preu = espais.get(number).getPreu();
            String username = espais.get(number).getUsername();

            //Espai espai = new Espai(id, nom, descripcio, area, direccio, provincia, preu, username);

            Intent intent = new Intent(view.getContext(), VeureEspai.class);
            intent.putExtra("id", id);
            intent.putExtra("nom", nom);
            intent.putExtra("descripcio", descripcio);
            intent.putExtra("area", area);
            intent.putExtra("direccio", direccio);
            intent.putExtra("provincia", provincia);
            intent.putExtra("preu", preu);
            intent.putExtra("username", username);

            view.getContext().startActivity(intent);
        }
    }
}