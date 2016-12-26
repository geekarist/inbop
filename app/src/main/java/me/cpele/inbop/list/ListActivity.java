package me.cpele.inbop.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

public class ListActivity extends AppCompatActivity implements ListContract.View {

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

    private ListContract.Presenter mPresenter;
    private ListContract.Model mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = ListInjection.providePresenter();
        mModel = ListInjection.provideModel();
        mPresenter.attach(this, mModel);
        mModel.attach(mPresenter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        mModel.detach();
        super.onDestroy();
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

        mPresenter.onLoadPlaces();
    }

    @Override
    public void onPlacesLoaded(List<Place> places) {
        adapter.clear();
        adapter.addAll(places);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPlacesLoadingFail(Throwable t) {
        Log.w(TAG, "Error loading places", t);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorLoadingLayout.setVisibility(View.VISIBLE);
    }
}
