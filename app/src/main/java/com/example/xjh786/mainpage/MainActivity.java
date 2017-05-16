package com.example.xjh786.mainpage;

import android.app.Notification;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.xjh786.mainpage.R.id.expanded_menu;
import static com.example.xjh786.mainpage.R.id.nav_libCorner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String str_fullName = intent.getStringExtra("fullName");
        boolean b_isAdmin = intent.getBooleanExtra("isAdmin", false);

        String message = "Welcome to Astro Library \n" + str_fullName ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView Welcome = (TextView) findViewById(R.id.textView2);
        Welcome.setText(message);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menuu = navigationView.getMenu();
        menuu.findItem(R.id.nav_libCorner).setVisible(b_isAdmin); //set the visibility of the Librarian Corner Menu

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



    @Override
    public boolean onPrepareOptionsMenu(Menu activity_main_drawer)
    {
        //boolean isAdmin = false;
        //MenuItem register = activity_main_drawer.findItem(R.id.nav_libCorner);
        //register.setVisible(isAdmin);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_about)
        {
            Intent intent = new Intent (this, AboutActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_account)
        {

        }
        else if (id == R.id.nav_Library_Book)
        {

        }
        else if (id == R.id.nav_help)
        {

        }
        else if (id == R.id.nav_libCorner)
        {
        if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_Library_Book) {

        }else if (id == R.id.nav_help) {
            Intent intent = new Intent(MainActivity.this, loadingActivity.class);
            MainActivity.this.startActivity(intent);

        }else if (id == nav_libCorner) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
