package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlacePrice {

    String adult;
    String student;
    String child;

    PlacePrice() {
    }

    public String getAdult() {
        return adult;
    }

    public String getStudent() {
        return student;
    }

    public String getChild() {
        return child;
    }
}
