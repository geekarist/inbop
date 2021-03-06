package me.cpele.inbop.list;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.CustomApp;
import me.cpele.inbop.R;
import me.cpele.inbop.repository.PlacesRepository;
import me.cpele.inbop.utils.Asserting;

public class ListFragment extends Fragment implements ListAdapter.Listener {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.list_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.list_pb_loading)
    View mLoadingLayout;
    @BindView(R.id.list_ll_error_loading)
    View mErrorLoadingLayout;
    @BindView(R.id.list_sw)
    SwipeRefreshLayout mSwipeRefresh;

    private ListAdapter mAdapter;
    private PlacesRepository mRepository;

    @OnClick(R.id.list_bt_reload)
    public void onClickReload() {
        mRepository.refresh();
    }

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

        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ColumnSpacingDecoration());

        mRepository = CustomApp.getInstance().getPlacesRepository();

        mSwipeRefresh.setOnRefreshListener(() -> mRepository.refresh());

        ListViewModelFactory factory = new ListViewModelFactory(mRepository);
        ListViewModel viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);

        viewModel.getData().observe(this, resource -> {

            resource = Asserting.notNull(resource);

            mRecyclerView.setVisibility(resource.status.placesVisibility);
            mLoadingLayout.setVisibility(resource.status.loadingVisibility);
            mErrorLoadingLayout.setVisibility(resource.status.errorVisibility);

            if (resource.places != null) {
                mAdapter.refresh(resource.places);
            }

            if (resource.exception != null) {
                Log.w(TAG, "Error loading places", resource.exception);
            }

            mSwipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        mRecyclerView.setLayoutManager(LayoutManagerFactory.create(getContext()));
    }

    @Override
    public void toggleStar(String id) {
        ListViewModelFactory factory = new ListViewModelFactory(mRepository);
        ListViewModel viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);
        viewModel.toggleStar(id);
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
