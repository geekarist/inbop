package me.cpele.inbop.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.R;

public class ListActivity extends AppCompatActivity {

    private ListContract.Presenter mPresenter;
    private ListContract.Model mModel;
    private ListContract.View mView;

    @BindView(R.id.list_tb)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mPresenter = ListInjection.providePresenter();
        mModel = ListInjection.provideModel();
        mView = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fr);

        mView.attach(mPresenter);
        mPresenter.attach(mView, mModel);
        mModel.attach(mPresenter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        mModel.detach();
        mView.detach();
        super.onDestroy();
    }
}
