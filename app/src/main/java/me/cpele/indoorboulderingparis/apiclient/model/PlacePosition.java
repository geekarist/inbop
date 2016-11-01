package me.cpele.indoorboulderingparis.apiclient.model;

import org.parceler.Parcel;

@SuppressWarnings("WeakerAccess")
@Parcel
public class PlacePosition {

    String address;
    String transport;
    Double lon;
    Double lat;

    public PlacePosition() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "PlacePosition{" +
                "address='" + address + '\'' +
                ", transport='" + transport + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}