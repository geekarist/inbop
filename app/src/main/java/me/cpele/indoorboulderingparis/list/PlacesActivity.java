package me.cpele.indoorboulderingparis.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.model.Place;

public class PlacesActivity extends AppCompatActivity implements PlacesContract.View {

    private static final String TAG = PlacesActivity.class.getSimpleName();

    private PlacesAdapter adapter;

    @BindView(R.id.places_rv)
    RecyclerView recyclerView;
    @BindView(R.id.places_ll_loading)
    View loadingLayout;
    @BindView(R.id.places_ll_error_loading)
    View errorLoadingLayout;
    @BindView(R.id.places_tb)
    Toolbar toolbar;

    private PlacesPresenter presenter;
    private PlacesContract.Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PlacesPresenter();
        model = new PlacesModel();
        presenter.attach(this, model);

        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter.reload();

        adapter = new PlacesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detach();
        presenter = null;
    }

    @OnClick(R.id.places_bt_reload)
    public void onClickReload() {
        presenter.reload();
    }

    @Override
    public void displayProgressBar() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorLoadingLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void displayError() {
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayPlaces(List<Place> places) {
        adapter.addAll(places);
    }
}
