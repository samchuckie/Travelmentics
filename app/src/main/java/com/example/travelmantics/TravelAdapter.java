package com.example.travelmantics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private ArrayList<Travel> travelArrayList = new ArrayList<>();
    private Context context;
//    TravelAdapter(Context context){
//        this.context=context;
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewHolder = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.travel_items,viewGroup,false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.location_map.setText(travelArrayList.get(i).getLocation());
        viewHolder.location_name.setText(travelArrayList.get(i).getName());
        viewHolder.location_price.setText(String.valueOf(travelArrayList.get(i).getPrice()));
        Picasso.get().load(travelArrayList.get(i).getImage()).into(viewHolder.location_image);
    }

    @Override
    public int getItemCount() {
        if(travelArrayList.size()==0){
            return 0;
        }
        return travelArrayList.size() ;
    }

    void addTravel(Travel mechanic) {
        travelArrayList.add(mechanic);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView location_price,location_map,location_name;
        ImageView location_image;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            location_image = itemView.findViewById(R.id.location_image);
            location_map = itemView.findViewById(R.id.location_map);
            location_name = itemView.findViewById(R.id.location_name);
            location_price = itemView.findViewById(R.id.location_price);
        }
    }
}
