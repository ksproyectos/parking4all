package com.ksproyectos.parking4all;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ksproyectos.parking4all.Services.AuthService;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AuthService authService = AuthService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tarifas) {
            Intent intent = new Intent(this, TarifasListActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_caja) {
            Intent intent = new Intent(this, CajaActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_usuarios) {
            Intent intent = new Intent(this, UsuariosListActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_mensualidades) {
            Intent intent = new Intent(this, MensualidadesListActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_movimientos) {
            Intent intent = new Intent(this, MovimientosListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void setContentView(int layoutResID, String title) {
        setContentView(layoutResID);
        setTitle(title);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
