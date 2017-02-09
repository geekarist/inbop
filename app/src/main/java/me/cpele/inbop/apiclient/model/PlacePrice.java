package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class PlacePrice {

    String adult;
    String student;
    String child;

    public PlacePrice() {
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

    public PlacePrice setAdult(String adult) {
        this.adult = adult;
        return this;
    }

    public PlacePrice setChild(String child) {
        this.child = child;
        return this;
    }

    public PlacePrice setStudent(String student) {
        this.student = student;
        return this;
    }
}
