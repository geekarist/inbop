package me.cpele.indoorboulderingparis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.cpele.indoorboulderingparis.list.PlacesActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, PlacesActivity.class));
        finish();
    }
}
