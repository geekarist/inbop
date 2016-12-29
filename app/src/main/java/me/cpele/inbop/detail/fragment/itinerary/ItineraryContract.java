package me.cpele.inbop.detail.fragment.itinerary;

public interface ItineraryContract {

    interface Presenter {

        void attach(View view, Model model);
        void detach();
    }

    interface View {

        void attach(Presenter presenter);
        void detach();
    }

    interface Model {

        void attach(Presenter presenter);
        void detach();
    }
}
