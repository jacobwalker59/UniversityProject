package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

//Example Item represents each single item placed within the Vertical Recycler timetable view

public class ExampleItem {

    private int mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(int mImageResource, String mText1, String mText2 ){
        this.mImageResource=mImageResource;
        this.mText1=mText1;
        this.mText2=mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
