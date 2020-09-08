package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters.ExampleAdapter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Array List is set and placed within the layout manager
        // layout manager fixes the view for every item
        //further information seen here
        //https://abhiandroid.com/ui/adapter

        ArrayList<ExampleItem>exampleItems = new ArrayList<>();
        exampleItems.add(new ExampleItem(R.drawable.scafelpike,"Line 1","Line 2"));
        //exampleItems.add(new ExampleItem(R.drawable.derwent,"Line 1","Line 2"));
        exampleItems.add(new ExampleItem(R.drawable.scafelpike,"Line 1","Line 2"));
        exampleItems.add(new ExampleItem(R.drawable.ullswater,"Line 1","Line 2"));

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ExampleAdapter(exampleItems);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }
}
