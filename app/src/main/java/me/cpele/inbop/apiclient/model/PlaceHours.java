package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlaceHours {

    PlaceHoursByDays weekdays;
    PlaceHoursByDays weekend;

    public PlaceHours() {
    }

    public PlaceHoursByDays getWeekdays() {
        return weekdays;
    }

    public PlaceHours setWeekdays(PlaceHoursByDays weekdays) {
        this.weekdays = weekdays;
        return this;
    }

    public PlaceHoursByDays getWeekend() {
        return weekend;
    }
}
