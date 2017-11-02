package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@SuppressWarnings({"WeakerAccess", "SimplifiableIfStatement"})
@Parcel
public class PlacePosition {

    String address;
    String transport;
    public Double lon;
    public Double lat;

    public PlacePosition() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return "PlacePosition{" +
                "address='" + address + '\'' +
                ", transport='" + transport + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public Double getLon() {
        return lon;
    }

    public PlacePosition setLon(Double lon) {
        this.lon = lon;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public PlacePosition setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlacePosition that = (PlacePosition) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (transport != null ? !transport.equals(that.transport) : that.transport != null)
            return false;
        if (lon != null ? !lon.equals(that.lon) : that.lon != null) return false;
        return lat != null ? lat.equals(that.lat) : that.lat == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (transport != null ? transport.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        return result;
    }
}