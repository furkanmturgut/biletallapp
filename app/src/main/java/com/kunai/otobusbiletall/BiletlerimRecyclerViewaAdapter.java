package com.kunai.otobusbiletall;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BiletlerimRecyclerViewaAdapter extends RecyclerView.Adapter<BiletlerimRecyclerViewaAdapter.BiletlerimHolder> {
    ArrayList<String> seyahatBilgiList;
    ArrayList<String> tarihBilgiList;
    ArrayList<String> saatBilgiList;
    ArrayList<String> fiyatBilgiList;

    public BiletlerimRecyclerViewaAdapter(ArrayList<String> seyahatBilgiList,ArrayList<String> tarihBilgiList,ArrayList<String> saatBilgiList,ArrayList<String> fiyatBilgiList){
        this.seyahatBilgiList = seyahatBilgiList;
        this.tarihBilgiList = tarihBilgiList;
        this.saatBilgiList = saatBilgiList;
        this.fiyatBilgiList = fiyatBilgiList;
    }

    @NonNull
    @Override
    public BiletlerimRecyclerViewaAdapter.BiletlerimHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.biletlerimdrawerrv,parent,false);

        return new BiletlerimHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiletlerimRecyclerViewaAdapter.BiletlerimHolder holder, int position) {
            holder.seyahatBilgi.setText(seyahatBilgiList.get(position));
            holder.tarihBilgi.setText(tarihBilgiList.get(position));
            holder.saatBilgi.setText(saatBilgiList.get(position));
            holder.fiyatBilgi.setText(fiyatBilgiList.get(position));
    }

    @Override
    public int getItemCount() {
        return seyahatBilgiList.size();
    }

    public class BiletlerimHolder extends RecyclerView.ViewHolder {

        TextView seyahatBilgi,saatBilgi,fiyatBilgi,tarihBilgi;

        public BiletlerimHolder(@NonNull View itemView) {
            super(itemView);
            seyahatBilgi = itemView.findViewById(R.id.seyahatBilgi);
            saatBilgi = itemView.findViewById(R.id.saatBilgi);
            fiyatBilgi = itemView.findViewById(R.id.fiyatBilgi);
            tarihBilgi = itemView.findViewById(R.id.tarihBilgi);

        }
    }
}
