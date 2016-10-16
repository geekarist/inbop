package me.cpele.indoorboulderingparis.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlaceHours {

    PlaceHoursByDays weekdays;
    PlaceHoursByDays weekend;

    PlaceHours() {
    }

    public PlaceHoursByDays getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(PlaceHoursByDays weekdays) {
        this.weekdays = weekdays;
    }

    public PlaceHoursByDays getWeekend() {
        return weekend;
    }
}
