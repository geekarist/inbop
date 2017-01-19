package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class Place {

    String id;
    String name;
    String city;
    String imgUrl;
    PlacePosition position;
    PlaceHours hours;
    PlacePrice price;
    String description;
    String url;
    String facebook;
    String email;

    public Place() {
    }

    public String getId() {
        return id;
    }

    public Place setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Place setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public PlacePosition getPosition() {
        return position;
    }

    public void setPosition(PlacePosition position) {
        this.position = position;
    }

    public PlaceHours getHours() {
        return hours;
    }

    public Place setHours(PlaceHours hours) {
        this.hours = hours;
        return this;
    }

    public PlacePrice getPrice() {
        return price;
    }

    public Place setPrice(PlacePrice price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Place setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFacebook() {
        return facebook;
    }

    public Place setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setAddress(String address) {
        this.position.setAddress(address);
    }

    public void setTransport(String transport) {
        this.position.setTransport(transport);
    }

    public String getEmail() {
        return email;
    }

    public Place setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", position=" + position +
                ", hours=" + hours +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", facebook='" + facebook + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
