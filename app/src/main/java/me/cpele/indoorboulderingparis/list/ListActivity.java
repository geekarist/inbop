package me.cpele.indoorboulderingparis.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.indoorboulderingparis.CustomApp;
import me.cpele.indoorboulderingparis.R;
import me.cpele.indoorboulderingparis.apiclient.PlacesService;
import me.cpele.indoorboulderingparis.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = ListActivity.class.getSimpleName();

    private ListAdapter adapter;

    @BindView(R.id.places_rv)
    RecyclerView recyclerView;
    @BindView(R.id.places_ll_loading)
    View loadingLayout;
    @BindView(R.id.places_ll_error_loading)
    View errorLoadingLayout;
    @BindView(R.id.places_tb)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        reload();

        adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.places_bt_reload)
    public void onClickReload() {
        reload();
    }

    private void reload() {

        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorLoadingLayout.setVisibility(View.INVISIBLE);

        PlacesService placesService = CustomApp.getInstance().getPlacesService();

        placesService.findAll().enqueue(new Callback<PlaceList>() {

            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
                adapter.addAll(response.body().getPlaces());
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                errorLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                errorLoadingLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
