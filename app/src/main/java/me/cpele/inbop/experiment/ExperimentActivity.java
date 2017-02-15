package me.cpele.inbop.experiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;

public class ExperimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_experiment);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.experiment_bt_get_from_graph)
    void onClickGetFromGraph() {

        Toast.makeText(this, "Yo", Toast.LENGTH_SHORT).show();
    }

    public static void addMenuItemTo(Menu menu) {

        menu.add("Experimentations");
    }

    public static void startEventually(MenuItem item, Context context) {

        if ("Experimentations".equals(item.getTitle())) {

            Intent intent = newIntent(context);
            context.startActivity(intent);
        }
    }

    private static Intent newIntent(Context context) {
        return new Intent(context, ExperimentActivity.class);
    }
}
