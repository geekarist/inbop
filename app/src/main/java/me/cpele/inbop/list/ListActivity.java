package me.cpele.inbop.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.BuildConfig;
import me.cpele.inbop.R;
import me.cpele.inbop.experiment.ExperimentActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (BuildConfig.DEBUG) {
            ExperimentActivity.addMenuItemTo(menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (BuildConfig.DEBUG) {
            ExperimentActivity.startEventually(item, this);
        }

        return super.onOptionsItemSelected(item);
    }
}
