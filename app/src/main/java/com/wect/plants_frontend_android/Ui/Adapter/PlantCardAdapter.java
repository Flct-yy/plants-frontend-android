package com.wect.plants_frontend_android.Ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wect.plants_frontend_android.Data.Model.PlantCard;
import com.wect.plants_frontend_android.R;

import java.util.List;

public class PlantCardAdapter extends RecyclerView.Adapter<PlantCardAdapter.ViewHolder> {

    private List<PlantCard> plantList;

    public PlantCardAdapter(List<PlantCard> plantList) {
        this.plantList = plantList;
    }

    public void setPlantList(List<PlantCard> plantList) {
        this.plantList = plantList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_plant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantCard plant = plantList.get(position);

        holder.cardImg.setImageResource(plant.getImageResId());
        holder.collectImg.setImageResource(plant.getCollect() ? R.drawable.ic_collected : R.drawable.ic_uncollect);
        holder.cardPlant.setText(plant.getPlantName());
        holder.cardLatin.setText(plant.getLatinName());
        holder.cardEnglish.setText(plant.getEnglishName());
        holder.cardAlias.setText(plant.getAlias());
        holder.cardClassify.setText(plant.getClassify());
        holder.cardProtection.setText(plant.getProtection());
        holder.cardCharacteristic.setText(plant.getCharacteristic());
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImg, collectImg;
        TextView cardPlant, cardLatin, cardEnglish, cardAlias, cardClassify, cardProtection, cardCharacteristic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.card_img);
            collectImg = itemView.findViewById(R.id.card_collect);
            cardPlant = itemView.findViewById(R.id.card_plant);
            cardLatin = itemView.findViewById(R.id.card_latin);
            cardEnglish = itemView.findViewById(R.id.card_english);
            cardAlias = itemView.findViewById(R.id.card_alias);
            cardClassify = itemView.findViewById(R.id.card_classify);
            cardProtection = itemView.findViewById(R.id.card_protection);
            cardCharacteristic = itemView.findViewById(R.id.card_characteristic);
        }
    }
}
