package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlaceHoursByDays {

    String opening;
    String closing;

    public PlaceHoursByDays() {
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getClosing() {
        return closing;
    }
}
