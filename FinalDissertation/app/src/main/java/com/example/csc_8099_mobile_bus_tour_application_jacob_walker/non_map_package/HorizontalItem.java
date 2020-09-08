package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

public class HorizontalItem {

    //Represents a single element for the Horizontal Recycler View
    //Get and set methods as per

    private int mHorizontalImageResource;
    private String mHorizontalText1;
    private boolean selected;

    public HorizontalItem(int mHorizontalImageResource, String mHorizontalText1, boolean selected) {
        this.mHorizontalImageResource = mHorizontalImageResource;
        this.mHorizontalText1 = mHorizontalText1;
        this.selected=selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getmHorizontalImageResource() {
        return mHorizontalImageResource;
    }

    public void setmHorizontalImageResource(int mHorizontalImageResource) {
        this.mHorizontalImageResource = mHorizontalImageResource;
    }

    public String getmHorizontalText1() {
        return mHorizontalText1;
    }

    public void setmHorizontalText1(String mHorizontalText1) {
        this.mHorizontalText1 = mHorizontalText1;
    }
}
