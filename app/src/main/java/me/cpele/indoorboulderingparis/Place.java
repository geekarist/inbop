package me.cpele.indoorboulderingparis;

class Place {
    private final String name;
    private final String city;

    Place(String name, String city) {
        this.name = name;
        this.city = city;
    }

    String getName() {
        return name;
    }

    String getCity() {
        return city;
    }
}
