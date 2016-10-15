package me.cpele.indoorboulderingparis;

class Place {
    private final String name;
    private final String city;
    private final String imgUrl;

    Place(String name, String city, String imgUrl) {
        this.name = name;
        this.city = city;
        this.imgUrl = imgUrl;
    }

    String getName() {
        return name;
    }

    String getCity() {
        return city;
    }

    String getImgUrl() {
        return imgUrl;
    }
}
