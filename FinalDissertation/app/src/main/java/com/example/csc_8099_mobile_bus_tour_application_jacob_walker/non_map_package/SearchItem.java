package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

//Simple Class with get and set methods
//used to get specific image and name of place and pass into Info Window
//each window allocated to place of interest type

public class SearchItem {

    private String searchName;
    private int searchImage;

    public SearchItem(String searchName, int searchImage) {
        this.searchName = searchName;
        this.searchImage = searchImage;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getSearchImage() {
        return searchImage;
    }

    public void setSearchImage(int searchImage) {
        this.searchImage = searchImage;
    }
}
