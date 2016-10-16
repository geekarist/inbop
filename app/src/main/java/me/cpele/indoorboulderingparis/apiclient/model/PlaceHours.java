package me.cpele.indoorboulderingparis.apiclient.model;

import org.parceler.Parcel;

@Parcel
class PlaceHours {

    PlaceHoursByDays weekdays;
    PlaceHoursByDays weekend;

    PlaceHours() {
    }
}
