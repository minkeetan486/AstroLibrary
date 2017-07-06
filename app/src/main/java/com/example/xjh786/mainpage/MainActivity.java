package com.example.xjh786.mainpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.xjh786.mainpage.adapter.AnnouncementAdapter;
import com.example.xjh786.mainpage.adapter.CustomListAdapter;
import com.example.xjh786.mainpage.model.Announcement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.xjh786.mainpage.R.id.nav_libCorner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String str_fullName, str_email, str_dept, str_coreId;
    static int isAdmin;
    static boolean b_isAdmin = false;
    private static String TAG = "MainActivity";
    private List<Announcement> announcementList = new ArrayList<>();
    private ProgressDialog pDialog;
    private AnnouncementAdapter adapter;
    private ListView announcemnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        str_fullName = intent.getStringExtra("fullName");
        isAdmin = intent.getIntExtra("isAdmin", 0);
        str_email = intent.getStringExtra("email");
        str_dept = intent.getStringExtra("dept");
        str_coreId = intent.getStringExtra("coreid");

        if (isAdmin == 0) {
            b_isAdmin = false;
        } else {
            b_isAdmin = true;
        }

        String message = "Welcome to Astro Library \n" + str_fullName;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView Welcome = (TextView) findViewById(R.id.textView2);
        Welcome.setText(message);

        announcemnet = (ListView) findViewById(R.id.announcement);
        adapter = new AnnouncementAdapter(this, announcementList);
        announcemnet.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        GetAnnouncement(str_coreId);

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

    public void GetAnnouncement(String etCoreId) {
        final String str_CoreId = etCoreId;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "annoucement: receive response " + response);

                hidePDialog();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onClickSearchBooks: receive response");

                    if (b_success) {
                        //Log.d(TAG, response);
                        JSONArray array = jsonResponse.getJSONArray("result");

                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                Announcement ann = new Announcement();
                                ann.setContent(obj.getString("Announcement"));
                                ann.setDate(obj.getString("Created_Date"));
                                announcementList.add(ann);
                                Collections.reverse(announcementList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged(); // update the listview
                    } else {
                        Log.d(TAG, response);
                        Log.d(TAG, "onClickSearchBooks: No results");
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Nothing found..")
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        Log.d(TAG, "onClickSearchBooks: sending request");

        AnnouncementRequest announcementRequest = new AnnouncementRequest(str_CoreId, responseListener);//remove core id and get from intent
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(announcementRequest);

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
    public boolean onPrepareOptionsMenu(Menu activity_main_drawer) {
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

        if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_Library_Book) {
            Intent intent = new Intent(MainActivity.this, LibraryList.class);
            MainActivity.this.startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(MainActivity.this, loadingActivity.class);
            MainActivity.this.startActivity(intent);

        } else if (id == nav_libCorner) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
