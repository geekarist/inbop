package me.cpele.inbop.experiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static java.lang.String.format;

public class ExperimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_experiment);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.experiment_bt_get_from_graph)
    void onClickGetFromGraph() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://graph.facebook.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GraphApiService graphApiService = retrofit.create(GraphApiService.class);

        final String nodeId = "me";
        graphApiService.get(nodeId).enqueue(new Callback<GraphApiNode>() {

            @Override
            public void onResponse(Call<GraphApiNode> call, Response<GraphApiNode> response) {
                GraphApiNode node = response.body();
                Toast.makeText(ExperimentActivity.this, String.valueOf(node), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GraphApiNode> call, Throwable t) {
                String msg = format("Error getting node [%s]", nodeId);
                Toast.makeText(ExperimentActivity.this, msg, Toast.LENGTH_LONG).show();
                Log.e(ExperimentActivity.class.getSimpleName(), msg, t);
            }
        });
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
        Call<GraphApiNode> get(@Path("nodeId") String nodeId);
    }

    private static class GraphApiNode {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "GraphApiNode{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
