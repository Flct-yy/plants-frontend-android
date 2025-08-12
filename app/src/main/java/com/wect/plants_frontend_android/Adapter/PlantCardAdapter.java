package com.wect.plants_frontend_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wect.plants_frontend_android.Model.NormalArticle;
import com.wect.plants_frontend_android.Model.PlantCard;
import com.wect.plants_frontend_android.R;

import java.util.List;

public class PlantCardAdapter extends RecyclerView.Adapter<PlantCardAdapter.ViewHolder> {

    private Context context;
    private List<PlantCard> plantList;

    public PlantCardAdapter(Context context, List<PlantCard> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_plant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantCard plant = plantList.get(position);

        holder.cardImg.setImageResource(plant.getImageResId());
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
        ImageView cardImg;
        TextView cardPlant, cardLatin, cardEnglish, cardAlias, cardClassify, cardProtection, cardCharacteristic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.card_img);
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
