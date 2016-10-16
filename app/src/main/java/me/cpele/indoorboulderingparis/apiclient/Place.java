package me.cpele.indoorboulderingparis.apiclient;

public class Place {
    private final String name;
    private final String city;
    private final String imgUrl;

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
