package me.cpele.inbop.list;

import android.support.annotation.NonNull;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

public class ListResource {

    public final Throwable exception;
    public final List<Place> places;
    public final Status status;

    private ListResource(@NonNull Throwable exception) {
        this.exception = exception;
        places = null;
        status = Status.ERROR;
    }

    private ListResource(List<Place> places) {
        this.places = places;
        exception = null;
        status = Status.SUCCESS;
    }

    private ListResource() {
        places = null;
        exception = null;
        status = Status.LOADING;
    }

    public static ListResource error(Throwable exception) {
        return new ListResource(exception);
    }

    public static ListResource success(List<Place> places) {
        return new ListResource(places);
    }

    public static ListResource loading() {
        return new ListResource();
    }

    public enum Status {
        SUCCESS, LOADING, ERROR
    }
}
