package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.UserLocation;

import java.util.List;

public class UserLocationAdapter extends ArrayAdapter<UserLocation> {

    //Whenever it is necessary to customize a list with objects of a specific type, in this case user location, it is necessary
    //to use an adapter accordingly.
    //further information can be found at https://abhianandroid.com/ui/adapter
    // most of the code found below was found at this page alongside
    //specifically https://abhianandroid.com/ui/arrayadapter-tutorial-example.html

    private int resourceLayout;
    private Context mContext;


    // whenever there is a list of single items that have been stored into an array, an array adapter is called.
    // adapters are bridges between a User Interface and a Data source, in this case an array.
    // first parameter below is context, which is used to make a reference to the current class or to the current activity.
    // resource is a simple integer value that makes reference towards a specific actvitiy using R.id....
    // lastly the array resource is placed in.

    public UserLocationAdapter(Context context, int resource, List<UserLocation> locations) {
        super(context, resource, locations);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    //below gets view relevant to any specific View in question alongside its parent.
    //used generally and is multipurpose. Found in coding in Flow blog
    // Youtube tutorial found here https://www.youtube.com/watch?v=_m-Ve-BAYe0&t=907s

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

        UserLocation locations = getItem(position);
        // uses location get and set method to set location name in the notification list.
        if (locations != null) {
            TextView address = (TextView) v.findViewById(R.id.address);
            TextView go = (TextView) v.findViewById(R.id.go);

                address.setText(locations.getName());


        }

        return v;
    }

}
