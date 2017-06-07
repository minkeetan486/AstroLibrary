package com.example.xjh786.mainpage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAccountActivity extends AppCompatActivity {

    private static final String TAG = "LibraryApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        //START--- Work In Progress: Hardcode for now...
        final String str_CoreId = "vbp687";
        final String str_Password = "vbp687";
        //END----- Work In Progress: Hardcode for now...

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onCreate MyAccount: receive response");

                    if(b_success){
                        Log.d(TAG, "onCreate MyAccount: success");
                        String str_fullName = jsonResponse.getJSONObject("result").getString("fullName");
                        String str_email = jsonResponse.getJSONObject("result").getString("email");
                        String str_department = jsonResponse.getJSONObject("result").getString("department");

                        TextView myAccountFullNameTextView = (TextView)findViewById(R.id.myAccountFullName);
                        myAccountFullNameTextView.setText("Full Name : " + str_fullName);
                        TextView myAccountEmailTextView = (TextView)findViewById(R.id.myAccountEmail);
                        myAccountEmailTextView.setText("Email : " + str_email);
                        TextView myAccountDepartmentTextView = (TextView)findViewById(R.id.myAccountDepartment);
                        myAccountDepartmentTextView.setText("Department : " + str_department);

                    }else{
                        Log.d(TAG, "onCreate MyAccount: fail");
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyAccountActivity.this);
                        builder.setMessage("Failed getting My Account info")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.d(TAG, "onCreate MyAccount: sending request");

        MyAccountRequest myAccountRequest = new MyAccountRequest(str_CoreId, str_Password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAccountActivity.this);
        queue.add(myAccountRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
