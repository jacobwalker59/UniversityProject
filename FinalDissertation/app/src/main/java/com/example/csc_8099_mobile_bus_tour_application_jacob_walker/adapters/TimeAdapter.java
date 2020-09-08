package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.BusStop;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.ArrayList;

//Repetition of User Location Adapter
//Follows example from the following
//https://www.youtube.com/watch?v=Vyqz_-sJGFk
//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example

public class TimeAdapter extends ArrayAdapter<BusStop> {

private LayoutInflater mInflator;
private ArrayList<BusStop> busList;
private int mViewResourceID;


    public TimeAdapter(Context context, int resource, ArrayList<BusStop>busList) {
        super(context, resource, busList);
        this.busList=busList;
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceID = resource;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflator.inflate(mViewResourceID, null);
        BusStop busStop = busList.get(position);

        if(busStop !=null){
            TextView busName = (TextView)convertView.findViewById(R.id.busStopName);
            TextView busApproach = (TextView)convertView.findViewById(R.id.busStopPickUp);
            TextView busReturn = (TextView)convertView.findViewById(R.id.busStopReturn);

            if(busName !=null){
                busName.setText(busStop.getBusStopName());

            }
            if(busApproach !=null){
                busApproach.setText(busStop.getApproach());

            }
            if(busReturn !=null){
                busReturn.setText(busStop.getReturning());

            }

        }
        return convertView;


    }
}
