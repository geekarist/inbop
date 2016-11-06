package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@Parcel
public class Place {

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

    Place() {
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public void setHours(PlaceHours hours) {
        this.hours = hours;
    }

    public PlacePrice getPrice() {
        return price;
    }

    public void setPrice(PlacePrice price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setFacebook(String facebook) {
        this.facebook = facebook;
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

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
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
