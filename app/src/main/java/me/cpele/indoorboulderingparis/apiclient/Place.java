package me.cpele.indoorboulderingparis.apiclient;

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
}
