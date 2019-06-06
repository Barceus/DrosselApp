package com.example.drosselapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BlackboardAdapter extends RecyclerView.Adapter<BlackboardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BlackboardItem> blackboardItems;
    private OnItemClickListener callback;

    BlackboardAdapter(Context context, ArrayList<BlackboardItem> blackboardItems, OnItemClickListener callback){
        this.context = context;
        this.blackboardItems = blackboardItems;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.blackboard_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String price = blackboardItems.get(i).getPrice().equals("0")?"FREE":blackboardItems.get(i).getPrice() + " DKK";
        String location = blackboardItems.get(i).getLocation().equals("SV")?"Student Village":"Kamtjatka";
        String type = "Borrow";
        switch (blackboardItems.get(i).getType()){
            case "0":
                break;
            case "1":
                type = "Looking For";
                break;
            case "2":
                type = "Sell";
                break;
        }
        viewHolder.name.setText(blackboardItems.get(i).getName());
        viewHolder.location.setText(location);
        viewHolder.smalldesc.setText(blackboardItems.get(i).getSmallDesc());
        viewHolder.type.setText(type);
        viewHolder.date.setText(blackboardItems.get(i).getDate());
        viewHolder.price.setText(price);
    }

    @Override
    public int getItemCount() {
        return blackboardItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,location,smalldesc,type,date,price;

        ViewHolder(@NonNull View itemView)  {
            super(itemView);

            name = itemView.findViewById(R.id.bbName);
            location = itemView.findViewById(R.id.bbLocation);
            smalldesc = itemView.findViewById(R.id.bbDesc);
            type = itemView.findViewById(R.id.bbType);
            date = itemView.findViewById(R.id.bbDate);
            price = itemView.findViewById(R.id.bbPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callback.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}
