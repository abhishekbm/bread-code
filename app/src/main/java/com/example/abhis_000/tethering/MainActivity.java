package com.example.abhis_000.tethering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent tetherSettings = new Intent();
        tetherSettings.setClassName("com.android.settings", "com.android.settings.TetherSettings");
        startActivity(tetherSettings);

        finish();
    }
}
