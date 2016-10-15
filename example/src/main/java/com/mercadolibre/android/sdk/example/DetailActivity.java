package com.mercadolibre.android.sdk.example;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String task_detail = intent.getStringExtra("task");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
        query.whereEqualTo("title", task_detail);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                ArrayList<String> aux = new ArrayList<String>();

                if (e == null) {
                    Log.d("Detail: ", object.size() + "instances");
                    String description = object.get(0).getString("description");
                    String location = object.get(0).getString("location");
                    TextView detail_textview = (TextView) findViewById(R.id.task_detail);
                    TextView detail_location_textview = (TextView) findViewById(R.id.task_detail_location);
                    detail_textview.setText(description);
                    detail_location_textview.setText(location);

                } else {
                    Log.d("Detail: ", e + " Happened");
                }

            }
        });
    }
}
