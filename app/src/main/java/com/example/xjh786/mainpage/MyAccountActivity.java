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
    String str_fullName, str_email, str_department;
    String str_CoreId, str_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        str_fullName = intent.getStringExtra("name");
        str_email = intent.getStringExtra("email");
        str_department = intent.getStringExtra("department");


        TextView myAccountFullNameTextView = (TextView)findViewById(R.id.myAccountFullName);
        myAccountFullNameTextView.setText("Full Name : " + MainActivity.str_fullName);
        TextView myAccountEmailTextView = (TextView)findViewById(R.id.myAccountEmail);
        myAccountEmailTextView.setText("Email : " + MainActivity.str_email);
        TextView myAccountDepartmentTextView = (TextView)findViewById(R.id.myAccountDepartment);
        myAccountDepartmentTextView.setText("Department : " + MainActivity.str_dept);

        /*//START--- Work In Progress: Hardcode for now...
        str_CoreId = "vbp687";
        str_Password = "vbp687";
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
        queue.add(myAccountRequest);*/
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
