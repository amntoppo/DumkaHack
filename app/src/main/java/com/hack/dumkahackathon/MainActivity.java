package com.hack.dumkahackathon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle dtoggle;
    private NavigationView nview;


    SessionManager session;
    public static final String PREFS = "Aadharlogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //permissions
        PackageManager mPackageManager = this.getPackageManager();
        int hasPermStorage = mPackageManager.checkPermission(android.Manifest.permission.CAMERA, this.getPackageName());

        if (hasPermStorage != PackageManager.PERMISSION_GRANTED) {
             Toast.makeText(getApplicationContext(), "No permission", Toast.LENGTH_LONG).show();

             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

        } else if (hasPermStorage == PackageManager.PERMISSION_GRANTED) {
             Toast.makeText(getApplicationContext(), "Has permission", Toast.LENGTH_LONG).show();

        } else {

        }
        //permission taken

        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        Log.e("scan", "initiated");
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        dtoggle = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(dtoggle);
        dtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nview = (NavigationView)findViewById(R.id.nv);
        nview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.news:
                        //do something;
                    case R.id.events:
                        //do something;
                    case R.id.game:
                        //do something
                    default:
                            return true;
                }
            }
        });
    }
}
