package me.cpele.inbop.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;

public class ListFragment extends Fragment {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.list_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.list_pb_loading)
    View mLoadingLayout;
    @BindView(R.id.list_ll_error_loading)
    View mErrorLoadingLayout;

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

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ColumnSpacingDecoration());

        ListModel model = CustomApp.getInstance().getListModel();
        ListPresenterFactory factory = new ListPresenterFactory(model);
        ListViewModel viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);

        viewModel.getData().observe(this, resource -> {

            assert resource != null;

            mRecyclerView.setVisibility(resource.status.placesVisibility);
            mLoadingLayout.setVisibility(resource.status.loadingVisibility);
            mErrorLoadingLayout.setVisibility(resource.status.errorVisibility);

            if (resource.places != null) {
                mAdapter.clear();
                mAdapter.addAll(resource.places);
            }

            if (resource.exception != null) {
                Log.w(TAG, "Error loading places", resource.exception);
            }
        });

        setupOrientation();

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();

        setupOrientation();
    }

    private void setupOrientation() {

        int orientation = getResources().getConfiguration().orientation;
        boolean landscape = orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (landscape) mLayoutManager = new GridLayoutManager(getContext(), 2);
        else mLayoutManager = new LinearLayoutManager(getContext());
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
