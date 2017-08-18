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

    public PlaceHoursByDays setOpening(String opening) {
        this.opening = opening;
        return this;
    }

    public String getClosing() {
        return closing;
    }

    public PlaceHoursByDays setClosing(String closing) {
        this.closing = closing;
        return this;
    }
}
