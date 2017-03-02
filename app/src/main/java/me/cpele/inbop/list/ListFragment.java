package me.cpele.inbop.list;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;
import me.cpele.inbop.apiclient.model.Place;

public class ListFragment extends Fragment implements ListContract.View {

    private static final String TAG = ListActivity.class.getSimpleName();

    @BindView(R.id.list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.list_pb_loading)
    View loadingLayout;
    @BindView(R.id.list_ll_error_loading)
    View errorLoadingLayout;

    @Nullable
    private ListContract.Presenter mPresenter;
    @NonNull
    private ListAdapter mAdapter = new ListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager;

        switch (getResources().getConfiguration().orientation) {

            case Configuration.ORIENTATION_LANDSCAPE:
                layoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.addItemDecoration(new ColumnSpacingDecoration());
                break;

            default:
                layoutManager = new LinearLayoutManager(getContext());
        }

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void attach(ListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void detach() {
        mPresenter = null;
    }

    @Override
    public void onStart() {
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

        if (mPresenter != null) mPresenter.onLoadPlaces();
    }

    @Override
    public void onPlacesLoaded(List<Place> places) {
        mAdapter.clear();
        mAdapter.addAll(places);
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

    private class ColumnSpacingDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            CardView cardView = (CardView) view;

            int position = parent.getChildAdapterPosition(cardView);
            int colIdx = position % 2;
            TextView text = (TextView) cardView.findViewById(R.id.place_tv_name);
            String name = String.valueOf(text.getText());
            Log.d(ListFragment.this.getClass().getSimpleName(), name);

            int totalMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.place_cv_margin);
            int leftMargin;
            int rightMargin;
            GridLayoutManager.LayoutParams cardParams = (GridLayoutManager.LayoutParams) cardView.getLayoutParams();
            if (colIdx == 1) {
                leftMargin = totalMargin / 2;
                rightMargin = cardParams.rightMargin;
            } else {
                leftMargin = cardParams.leftMargin;
                rightMargin = totalMargin - totalMargin / 2;
            }

            cardParams.setMargins(leftMargin, cardParams.topMargin, rightMargin, cardParams.bottomMargin);

            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
