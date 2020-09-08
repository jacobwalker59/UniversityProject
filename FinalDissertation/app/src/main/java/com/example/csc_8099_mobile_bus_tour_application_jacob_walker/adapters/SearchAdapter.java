package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.SearchItem;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

import java.util.ArrayList;
import java.util.List;

//search adapter was taken from the following
//wanted to implement auto compelte search bar
// entire class taken from this youtube video and adapter
//https://www.youtube.com/watch?v=JB3ETK5mh3c&t=1s

public class SearchAdapter extends ArrayAdapter<SearchItem> {

    private List<SearchItem> adapterList;

    public SearchAdapter(Context context, List<SearchItem> searchItems) {
        super(context, 0,searchItems);
        this.adapterList = searchItems;


    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.map_autocomplete_row,parent,false
            );
        }

        TextView textView = convertView.findViewById(R.id.searchBarTextView);
        ImageView imageView = convertView.findViewById(R.id.searchImageView);
        SearchItem searchItem = getItem(position);
        if(searchItem !=null){
            textView.setText(searchItem.getSearchName());
            imageView.setImageResource(searchItem.getSearchImage());
        }
        return convertView;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<SearchItem> options = new ArrayList<>();

            if(charSequence==null || charSequence.length()==0){
                options.addAll(adapterList);

            }
            else{
                String filter = charSequence.toString().toLowerCase().trim();
                for(SearchItem item: adapterList)
                {
                    if(item.getSearchName().toLowerCase().contains(filter));
                    {
                        options.add(item);
                    }

                }
            }
                    results.values=options;
                    results.count=options.size();
                    return results;


        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((SearchItem)resultValue).getSearchName();
        }
    };
}
