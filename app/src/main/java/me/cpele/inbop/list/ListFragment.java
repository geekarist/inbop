package me.cpele.inbop.list;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

public class ListFragment extends Fragment {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.list_pb_loading)
    View loadingLayout;
    @BindView(R.id.list_ll_error_loading)
    View errorLoadingLayout;

    @NonNull
    private ListAdapter mAdapter = new ListAdapter();
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new ColumnSpacingDecoration());

        ListModel model = CustomApp.getInstance().getListModel();
        ListPresenterFactory factory = new ListPresenterFactory(model);
        ListPresenter presenter = ViewModelProviders.of(this, factory).get(ListPresenter.class);

        presenter.getData().observe(this, listResource -> {
            if (listResource == null) {
                fail(new NullPointerException("Resource should not be null"));
            } else if (listResource.status == ListResource.Status.SUCCESS) {
                load(listResource.places);
            } else if (listResource.status == ListResource.Status.ERROR) {
                fail(listResource.exception);
            }
        });

        setupOrientation();

        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void load(List<Place> places) {
        mAdapter.clear();
        mAdapter.addAll(places);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorLoadingLayout.setVisibility(View.GONE);
    }

    public void fail(Throwable t) {
        Log.w(TAG, "Error loading places", t);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void onSetupForPortrait() {
        mLayoutManager = new LinearLayoutManager(getContext());
    }

    public void onSetupForLandscape() {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
    }

    private void setupOrientation() {

//        int orientation = getResources().getConfiguration().orientation;
//        boolean landscape = orientation == Configuration.ORIENTATION_LANDSCAPE;
//        mPresenter.checkOrientation(landscape);

        onSetupForPortrait();
    }

    private static class ColumnSpacingDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            super.getItemOffsets(outRect, view, parent, state);

            int margin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.place_cv_margin);

            outRect.set(margin / 2, margin / 2, margin / 2, margin / 2);
        }
    }
}
