package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.HorizontalItem;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.ArrayList;

//Following is a replica of the other three classes, see User Location Adapter for full details as to spec

public class HorizontalRecyclerClassAdapter extends RecyclerView.Adapter<HorizontalRecyclerClassAdapter.ViewHolder> {

    private ArrayList<HorizontalItem> mViewHolderItems;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView textView1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.horizontal_image_View);
            textView1 = itemView.findViewById(R.id.horizontal_title);
        }
    }

    public HorizontalRecyclerClassAdapter(ArrayList<HorizontalItem> viewHolderItems){
        mViewHolderItems = viewHolderItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_view_item,parent,false);
        HorizontalRecyclerClassAdapter.ViewHolder viewHolder = new  HorizontalRecyclerClassAdapter.ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerClassAdapter.ViewHolder holder, int position) {

        HorizontalItem horizontalItem = mViewHolderItems.get(position);
        holder.mImageView.setImageResource(horizontalItem.getmHorizontalImageResource());
        holder.textView1.setText(horizontalItem.getmHorizontalText1());

        if(horizontalItem.isSelected())
            holder.itemView.setVisibility(View.VISIBLE);
        else
            holder.itemView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mViewHolderItems.size();
    }





}


