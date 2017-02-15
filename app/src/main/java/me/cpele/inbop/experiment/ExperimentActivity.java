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
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ExperimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_experiment);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.experiment_bt_get_from_graph)
    void onClickGetFromGraph() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.facebook.com").build();
        GraphApiService graphApiService = retrofit.create(GraphApiService.class);
        GraphApiNode node = graphApiService.get("me");
        Toast.makeText(this, String.valueOf(node), Toast.LENGTH_LONG).show();
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

    private interface GraphApiService {

        @GET("/v2.8/{nodeId}?fields=name")
        GraphApiNode get(@Path("nodeId") String nodeId);
    }

    private static class GraphApiNode {

        String id;
        String name;

        @Override
        public String toString() {
            return "GraphApiNode{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
