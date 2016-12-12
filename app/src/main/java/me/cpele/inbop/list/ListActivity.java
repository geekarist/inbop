package me.cpele.inbop.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.PlacesService;
import me.cpele.inbop.apiclient.model.PlaceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = ListActivity.class.getSimpleName();

    private ListAdapter adapter;

    @BindView(R.id.list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.list_pb_loading)
    View loadingLayout;
    @BindView(R.id.list_ll_error_loading)
    View errorLoadingLayout;
    @BindView(R.id.list_tb)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        reload();
    }

    @OnClick(R.id.list_bt_reload)
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
                adapter.clear();
                adapter.addAll(response.body().getPlaces());
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                errorLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
                Log.e(TAG, "Error loading places", t);
                loadingLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                errorLoadingLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
