package me.cpele.inbop.list;

import java.util.List;

import me.cpele.inbop.Consumer;
import me.cpele.inbop.apiclient.model.Place;

public class ListContract {

    interface Model {

    }

    interface View {

    }

    public interface Presenter {

        void attach(View view);

        void detach();

        void loadPlaces(Consumer<List<Place>> onSuccess, Consumer<Throwable> onError);
    }
}
