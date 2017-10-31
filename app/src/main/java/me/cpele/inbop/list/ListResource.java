package me.cpele.inbop.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ListResource {

    @Nullable
    public final Throwable exception;
    @Nullable
    public List<Place> places;
    @NonNull
    public final Status status;

    private ListResource(@NonNull Throwable exception) {
        this.exception = exception;
        places = null;
        status = Status.ERROR;
    }

    private ListResource(@NonNull List<Place> places) {
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
        SUCCESS(VISIBLE, GONE, GONE), LOADING(GONE, VISIBLE, GONE), ERROR(GONE, GONE, VISIBLE);

        public final int placesVisibility;
        public final int loadingVisibility;
        public final int errorVisibility;

        Status(int placesVisibility, int loadingVisibility, int errorVisibility) {
            this.placesVisibility = placesVisibility;
            this.loadingVisibility = loadingVisibility;
            this.errorVisibility = errorVisibility;
        }
    }
}
