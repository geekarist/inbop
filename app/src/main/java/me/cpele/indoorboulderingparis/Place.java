package me.cpele.indoorboulderingparis;

class Place {
    private final String mName;
    private final String mCity;

    Place(String mName, String mCity) {
        this.mName = mName;
        this.mCity = mCity;
    }

    String getName() {
        return mName;
    }

    String getCity() {
        return mCity;
    }
}
