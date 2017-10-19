package me.cpele.inbop.list;

import android.support.annotation.NonNull;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

public class ListResource {

    public final Throwable exception;
    public final List<Place> places;
    public final Status status;

    public ListResource(@NonNull Throwable exception) {
        this.exception = exception;
        places = null;
        status = Status.ERROR;
    }

    public ListResource(List<Place> places) {
        this.places = places;
        exception = null;
        status = Status.SUCCESS;
    }

    public static ListResource error(Throwable exception) {
        return new ListResource(exception);
    }

    public static ListResource success(List<Place> places) {
        return new ListResource(places);
    }

    public enum Status {
        SUCCESS, ERROR
    }
}
