package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models;

import java.io.Serializable;

//provides a photo of each of the places, further future functionality required
// could pass the image types in as opposed to hard copied images
//given late implementation needs must
//also irrelevant largely for paths or niche points of interest which would have to be done manually regardless

public class Photos implements Serializable {

    private String photo_reference;

    private String height;

    private String[] html_attributions;

    private String width;

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "ClassPojo [photo_reference = " + photo_reference + ", height = " + height + ", html_attributions = " + html_attributions + ", width = " + width + "]";
    }
}
