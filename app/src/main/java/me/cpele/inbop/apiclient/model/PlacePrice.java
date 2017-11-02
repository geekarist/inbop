package me.cpele.inbop.apiclient.model;

import org.parceler.Parcel;

@SuppressWarnings({"SameParameterValue", "SimplifiableIfStatement"})
@Parcel
public class PlacePrice {

    public String adult;
    public String student;
    public String child;

    public PlacePrice() {
    }

    public String getAdult() {
        return adult;
    }

    public String getStudent() {
        return student;
    }

    public String getChild() {
        return child;
    }

    public PlacePrice setAdult(String adult) {
        this.adult = adult;
        return this;
    }

    public PlacePrice setChild(String child) {
        this.child = child;
        return this;
    }

    public PlacePrice setStudent(String student) {
        this.student = student;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlacePrice that = (PlacePrice) o;

        if (adult != null ? !adult.equals(that.adult) : that.adult != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        return child != null ? child.equals(that.child) : that.child == null;
    }

    @Override
    public int hashCode() {
        int result = adult != null ? adult.hashCode() : 0;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }
}
