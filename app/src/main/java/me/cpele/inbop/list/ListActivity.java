package me.cpele.inbop.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cpele.inbop.R;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.list_tb)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }
}
