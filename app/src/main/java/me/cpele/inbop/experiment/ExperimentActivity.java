package me.cpele.inbop.experiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cpele.inbop.BuildConfig;
import me.cpele.inbop.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static java.lang.String.format;

public class ExperimentActivity extends AppCompatActivity {

    @BindView(R.id.experiment_tv_graph_info)
    TextView mGraphInfoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_experiment);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.experiment_bt_get_from_graph)
    void onClickGetFromGraph() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                message -> Log.d(ExperimentActivity.this.getClass().getSimpleName(), message));

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://graph.facebook.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        GraphApiService graphApiService = retrofit.create(GraphApiService.class);

        final String nodeId = "antrebloc94";
        String accessToken = BuildConfig.FACEBOOK_APP_ID + "|" + BuildConfig.FACEBOOK_SECRET_KEY;
        graphApiService.get(nodeId, accessToken).enqueue(new Callback<GraphApiNode>() {

            @Override
            public void onResponse(@NonNull Call<GraphApiNode> call, @NonNull Response<GraphApiNode> response) {
                GraphApiNode node = response.body();
                mGraphInfoText.setText(String.valueOf(node));
            }

            @Override
            public void onFailure(@NonNull Call<GraphApiNode> call, @NonNull Throwable t) {
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

        @GET("/v2.8/{nodeId}?fields=name,hours,location,website,emails,cover,link,parking,phone,public_transit,single_line_address")
        Call<GraphApiNode> get(@Path("nodeId") String nodeId, @Query("access_token") String accessToken);
    }

    private static class GraphApiNode {

        public String id;
        public String name;
        public Object hours;
        public Object location;
        public String website;
        public Object emails;
        public Object cover;
        public String link;
        public Object parking;
        public String phone;
        public String public_transit;
        public String single_line_address;

        @Override
        public String toString() {
            return "GraphApiNode{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", hours='" + hours + '\'' +
                    ", location='" + location + '\'' +
                    ", website='" + website + '\'' +
                    ", emails='" + emails + '\'' +
                    ", cover='" + cover + '\'' +
                    ", link='" + link + '\'' +
                    ", parking='" + parking + '\'' +
                    ", phone='" + phone + '\'' +
                    ", public_transit='" + public_transit + '\'' +
                    ", single_line_address='" + single_line_address + '\'' +
                    '}';
        }
    }
}
