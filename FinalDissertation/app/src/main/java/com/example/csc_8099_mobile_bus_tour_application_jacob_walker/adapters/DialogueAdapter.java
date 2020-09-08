package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.BusStop;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.List;

public class DialogueAdapter extends ArrayAdapter<BusStop> {

    private int resourceLayout;
    private Context mContext;
    //represents typical super class commonly found in Adapter.

    public DialogueAdapter(Context context, int resource, List<BusStop> locations) {
        super(context, resource, locations);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        // layout inflator used to instantiate an XML activity to its correspnding View objects.
        // is always used in regards to adapters and is neccesary component for user to view each object.

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        BusStop locations = getItem(position);
        // uses location get and set method to set location name in the notification list.
        if (locations != null) {
            TextView address = (TextView) v.findViewById(R.id.bus_approach_id);

            address.setText(locations.getBusStopName());

        }

        return v;
    }
}