package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;

//Loading page was used to compensate for time taken to load the map fragment
// taken from the following multiple places
//https://stackoverflow.com/questions/40865492/splash-screen-android-app-has-stopped/40865577#40865577
//https://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen

//splash screen is common within Android Development and the developer wished the app
// to look as professional as possible

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                permission();
            }
        }, 1500);

    }

    void ActivityCall() {

        Intent intent = new Intent(Loading.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    void permission() {

        if (Build.VERSION_CODES.LOLLIPOP_MR1 < Build.VERSION.SDK_INT) {
            ActivityCompat.requestPermissions(Loading.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            ActivityCall();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean connect = true;
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        connect = false;
                    }
                }
                if (connect) {
                    ActivityCall();
                } else {
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
