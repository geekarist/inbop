package me.cpele.indoorboulderingparis.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class Place {

    String name;
    String city;
    String imgUrl;
    String address;
    String transport;
    PlaceHours hours;
    PlacePrice price;
    String description;
    String url;

    Place() {
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getTransport() {
        return transport;
    }

    public PlaceHours getHours() {
        return hours;
    }

    public PlacePrice getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
