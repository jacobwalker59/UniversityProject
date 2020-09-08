package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.ExampleItem;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> mExampleItems;
    //takes an adaptor for individual items within the Information Windows marker
    //ImageViews and Text Views are set
    //check User Location Adapter Class which shows all Adapter related information
    //this class is a replica of the above

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView textView1;
        public TextView textView2;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.listTitle);
            textView2 = itemView.findViewById(R.id.textView2);

        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleItems){
        mExampleItems = exampleItems;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
        return  exampleViewHolder;
    }

    //binds the view to a single item
    //each item is an object within Array List
    //Adapter constantly iterates through the bottom, constantly setting the Image And Text Views

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        ExampleItem currentItem = mExampleItems.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());

    }

    @Override
    public int getItemCount() {
        return mExampleItems.size();
    }
}
