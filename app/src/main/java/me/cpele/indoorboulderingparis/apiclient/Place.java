package me.cpele.indoorboulderingparis.apiclient;

import org.parceler.Parcel;

@Parcel
public class Place {

    String name;
    String city;
    String imgUrl;

    Place() {
    }

    Place(String name, String city, String imgUrl) {
        this.name = name;
        this.city = city;
        this.imgUrl = imgUrl;
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
}
