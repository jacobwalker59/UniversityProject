package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters.TimeAdapter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.GoogleApiService;

import java.util.ArrayList;

public class TimeTableFragment extends Fragment {

    ArrayList<BusStop>arrayList;
    ListView listView;
    BusStop busStop;
    private String placeType = "";
    private GoogleApiService googleApiService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflator takes time table and sets it wihtin the layout, typical of Recycler Views
        //information taken from following source
        //https://www.youtube.com/watch?v=Vyqz_-sJGFk

        View view = inflater.inflate(R.layout.fragment_time_table,container,false);
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //places everything in Database as seen below
        //kept in commentary so supervisor can see what has been entered

        /*
        db.insertData("Brough Clock", "10:35","16:15");
        db.insertData("Winton", "10:41","16:10");
        db.insertData("Kirby Stephen", "10:50","16:00");e
        db.insertData("Kirby Stephen West Station", "10:55","15:53");
        db.insertData("Ravenstonedale School", "11:00","15:45");
        db.insertData("Newbiggin on Lune Village Hall", "11:05","15:40");
        db.insertData("Mount Pleasant, Tepay", "11:18","15:29");
        db.insertData("Barnaby Rudge, Tepay", "11:20","15:27");
        db.insertData("Grayrigg", "11:34","15:10");
        db.insertData("Kendal Morrisons", "11:45","15:00");
        db.insertData("Stricklandgate", "11:51","");
        db.insertData("Blackhall Road", "11:55","14:55");
        */
        arrayList = new ArrayList<>();
        Cursor cursor = db.viewData();
        int numRows = cursor.getCount();
        if(numRows==0){
            Toast.makeText(getActivity(),"DataBase Empty", Toast.LENGTH_SHORT).show();

        }
        else{
            while(cursor.moveToNext()){
                busStop = new BusStop(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(busStop);
            }
        }
        listView = (ListView)view.findViewById(R.id.time_list);


        TimeAdapter adapter = new TimeAdapter(getActivity(), R.layout.time_table_item,arrayList);
        listView.setAdapter(adapter);
        return view;
    }

}
