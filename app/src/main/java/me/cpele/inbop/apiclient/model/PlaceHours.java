package me.cpele.inbop.apiclient.model;

import android.arch.persistence.room.Embedded;

import org.parceler.Parcel;

@SuppressWarnings("SimplifiableIfStatement")
@Parcel
public class PlaceHours {

    @Embedded(prefix = "weekdays")
    public PlaceHoursByDays weekdays;
    @Embedded(prefix = "weekend")
    public PlaceHoursByDays weekend;

    public PlaceHours() {
    }

    public PlaceHoursByDays getWeekdays() {
        return weekdays;
    }

    public PlaceHours setWeekdays(PlaceHoursByDays weekdays) {
        this.weekdays = weekdays;
        return this;
    }

    public PlaceHoursByDays getWeekend() {
        return weekend;
    }

    public PlaceHours setWeekend(PlaceHoursByDays weekend) {
        this.weekend = weekend;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceHours that = (PlaceHours) o;

        if (weekdays != null ? !weekdays.equals(that.weekdays) : that.weekdays != null)
            return false;
        return weekend != null ? weekend.equals(that.weekend) : that.weekend == null;
    }

    @Override
    public int hashCode() {
        int result = weekdays != null ? weekdays.hashCode() : 0;
        result = 31 * result + (weekend != null ? weekend.hashCode() : 0);
        return result;
    }
}
