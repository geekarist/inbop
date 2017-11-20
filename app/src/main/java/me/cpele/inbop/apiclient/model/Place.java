package me.cpele.inbop.apiclient.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@SuppressWarnings({"SameParameterValue", "ConstantConditions", "SimplifiableIfStatement", "UnusedReturnValue"})
@Entity
public class Place {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String city;
    public String imgUrl;
    @Embedded
    public PlacePosition position;
    @Embedded
    public PlaceHours hours;
    @Embedded
    public PlacePrice price;
    public String description;
    public String url;
    public String facebook;
    public String email;
    public boolean starred;

    public Place() {
        this.id = UUID.randomUUID().toString();
    }

    @Ignore
    public Place(Place place) {
        this(place.id, place.name, place.city, place.imgUrl, place.position, place.hours,
                place.price, place.description, place.url, place.facebook, place.email,
                place.starred);
    }

    public Place(@NonNull String id, String name, String city, String imgUrl, PlacePosition position, PlaceHours hours, PlacePrice price, String description, String url, String facebook, String email, boolean starred) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.imgUrl = imgUrl;
        this.position = position;
        this.hours = hours;
        this.price = price;
        this.description = description;
        this.url = url;
        this.facebook = facebook;
        this.email = email;
        this.starred = starred;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Place setId(@NonNull String id) {
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

    public Place setPosition(PlacePosition position) {
        this.position = position;
        return this;
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

    public Place setUrl(String url) {
        this.url = url;
        return this;
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

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (starred != place.starred) return false;
        if (id != null ? !id.equals(place.id) : place.id != null) return false;
        if (name != null ? !name.equals(place.name) : place.name != null) return false;
        if (city != null ? !city.equals(place.city) : place.city != null) return false;
        if (imgUrl != null ? !imgUrl.equals(place.imgUrl) : place.imgUrl != null) return false;
        if (position != null ? !position.equals(place.position) : place.position != null)
            return false;
        if (hours != null ? !hours.equals(place.hours) : place.hours != null) return false;
        if (price != null ? !price.equals(place.price) : place.price != null) return false;
        if (description != null ? !description.equals(place.description) : place.description != null)
            return false;
        if (url != null ? !url.equals(place.url) : place.url != null) return false;
        if (facebook != null ? !facebook.equals(place.facebook) : place.facebook != null)
            return false;
        return email != null ? email.equals(place.email) : place.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (facebook != null ? facebook.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (starred ? 1 : 0);
        return result;
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
                ", starred=" + starred +
                '}';
    }
}
