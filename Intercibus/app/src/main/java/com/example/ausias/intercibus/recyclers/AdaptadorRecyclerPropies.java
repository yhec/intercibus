package com.example.ausias.intercibus.recyclers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ausias.intercibus.R;
import com.example.ausias.intercibus.VeureInfoPropietari;
import com.example.ausias.intercibus.VeureReserva;
import com.example.ausias.intercibus.classes.Reserva;

import java.util.List;

/**
 * Aquesta classe té l'objectiu d'adaptar les nostres dades al RecyclerView
 * @author Jessica Miranda
 * @version 1.0
 */
public class AdaptadorRecyclerPropies extends RecyclerView.Adapter <AdaptadorRecyclerPropies.ViewHolder>{

    public List<Reserva> reserves;

    /**
     * Constructor del adaptador
     * @param reserves Paràmetre que fa referència a una llista de reserves
     */
    public AdaptadorRecyclerPropies(List<Reserva> reserves) {

        this.reserves = reserves;
    }

    @Override
    public AdaptadorRecyclerPropies.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout. fila_mostra_reserva, parent, false);

        AdaptadorRecyclerPropies.ViewHolder vh = new AdaptadorRecyclerPropies.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerPropies.ViewHolder holder, int position) {

        String nom = reserves.get(position).getNomEspai();
        Bitmap foto = reserves.get(position).getFoto();
        int id = reserves.get(position).getIdReserva();
        float preu = reserves.get(position).getPreu();
        String entrada = reserves.get(position).getEntradaReserva();
        String sortida = reserves.get(position).getSortidaReserva();

        String preuString;
        if (preu % 1 == 0) {
            preuString = String.valueOf((int)preu);
        }
        else {
            preuString = String.valueOf(preu);
        }

        holder.fotoEspai.setImageBitmap(foto);
        holder.toolbarCard.setTitle(nom);
        String entradaText = holder.itemView.getContext().getResources().getString(R.string.entrada);
        String sortidaText = holder.itemView.getContext().getResources().getString(R.string.sortida);
        String preuText = holder.itemView.getContext().getResources().getString(R.string.preu);
        holder.descripcio.setText(entradaText + " " + entrada + '\n' + sortidaText + " " + sortida + '\n'+ preuText + " " + preuString + " €");
    }

    @Override
    public int getItemCount() {
        return reserves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView fotoEspai;
        TextView descripcio;
        Toolbar toolbarCard;
        LinearLayout layout;

        Button botoMesInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoEspai = itemView.findViewById(R.id.imageView_foto_reserva);
            toolbarCard = itemView.findViewById(R.id.toolbarCard);
            descripcio = itemView.findViewById(R.id.textViewInfo);
            layout = itemView.findViewById(R.id.layoutReserva);

            botoMesInfo = itemView.findViewById(R.id.btMesInfo);

            layout.setOnClickListener(this);
            botoMesInfo.setOnClickListener(this);
        }

        /**
         * Mètode que recull les dades d'una vista i crida a un altre activity passant aquests paràmetres
         * @param v Paràmetre que fa referència a una vista
         */
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.layoutReserva) {
                int number = this.getAdapterPosition();

                Intent intent = new Intent(v.getContext(), VeureInfoPropietari.class);
                Log.d("IDRESERVA", reserves.get(number).getIdReserva()+"");
                intent.putExtra("idReserva", reserves.get(number).getIdReserva());
                v.getContext().startActivity(intent);
            }

            else if (id == R.id.btMesInfo) {
                int number = this.getAdapterPosition();

                Intent intent = new Intent(v.getContext(), VeureReserva.class);
                intent.putExtra("idReserva", reserves.get(number).getIdReserva());
                v.getContext().startActivity(intent);
            }
        }
    }
}
