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
    String facebook;
    String email;

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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setHours(PlaceHours hours) {
        this.hours = hours;
    }

    public void setPrice(PlacePrice price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", address='" + address + '\'' +
                ", transport='" + transport + '\'' +
                ", hours=" + hours +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", facebook='" + facebook + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
