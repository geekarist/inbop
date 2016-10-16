package me.cpele.indoorboulderingparis.apiclient;

import org.parceler.Parcel;

@Parcel
class PlaceHours {

    PlaceHoursByDays weekdays;
    PlaceHoursByDays weekend;

    PlaceHours() {
    }
}
