package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;
import java.util.List;

//Class acts as parent class for Results class, which holds all information for entire POJO package

public class MyPlaces implements Serializable {

    private String next_page_token;

    private List<Results> results;

    private String[] html_attributions;

    private String status;

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [next_page_token = " + next_page_token + ", results = " + results + ", html_attributions = " + html_attributions + ", status = " + status + "]";
    }
}
