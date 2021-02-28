package com.kunai.otobusbiletall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BiletHolder> {
    private ArrayList<String> biletler ;
    private ArrayList<String> tarihler;
    private ArrayList<String> fiyatlar;


    public RecyclerViewAdapter(ArrayList<String> biletler, ArrayList<String>tarihler, ArrayList<String> fiyatlar){
        this.biletler = biletler;
        this.tarihler = tarihler;
        this.fiyatlar = fiyatlar;




    }

    @NonNull
    @Override
    public RecyclerViewAdapter.BiletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_bilet,parent,false);
        return new BiletHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.BiletHolder holder, int position) {
            holder.textViewYolculuk.setText(biletler.get(position));
            holder.textViewTarih.setText(tarihler.get(position));
            holder.textViewFiyat.setText(fiyatlar.get(position));


    }

    @Override
    public int getItemCount() {
        return tarihler.size();
    }

    public class BiletHolder extends RecyclerView.ViewHolder {
        TextView textViewTarih,textViewYolculuk,textViewFiyat;
        Button buttonSatinAl;
        Context context;
        public BiletHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            textViewTarih = itemView.findViewById(R.id.textViewTarih);
            textViewYolculuk = itemView.findViewById(R.id.textViewYolculuk);
            textViewFiyat = itemView.findViewById(R.id.textViewFiyat);
            buttonSatinAl = itemView.findViewById(R.id.buttonSatinAl);

            buttonSatinAl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,KoltukSecmeActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }



}
