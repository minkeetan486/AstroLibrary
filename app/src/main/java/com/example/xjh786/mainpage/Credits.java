package com.example.xjh786.mainpage;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Credits extends AppCompatActivity {

    private ArrayList<String> creditNames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        // instantiate creditNames
        creditNames = new ArrayList<String>();
        updateNewsList(); // update the list

        // this is array adapter
        // 1st parameter is the context of the activity
        // 2nd parameter is the type of list view
        // Default text color is black in list view. By using custom_textview, it allows us to change text color and so on
        // 3rd parameter is the array list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_textview, creditNames);

        // get the reference of version news
        ListView lvNames = (ListView) findViewById(R.id.listview_credits);
        lvNames.setAdapter(arrayAdapter);
    }

    // back to main activity when back button is pressed
 //   @Override
   // public void onBackPressed()
   // {
    //    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //    startActivity(intent);
  //  }

    // update names to array list
    private void updateNewsList ()
    {
        // get name list from string.xml -> credit_names
        Resources res = getResources();
        String[] names = res.getStringArray(R.array.credit_names);

        for(String name : names)
        {
            creditNames.add(name);
        }
    }
}
