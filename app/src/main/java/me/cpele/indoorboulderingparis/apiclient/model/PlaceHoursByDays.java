package me.cpele.indoorboulderingparis.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlaceHoursByDays {

    String opening;
    String closing;

    PlaceHoursByDays() {
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
