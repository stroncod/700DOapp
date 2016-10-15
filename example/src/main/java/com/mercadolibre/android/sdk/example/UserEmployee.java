package com.mercadolibre.android.sdk.example;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.MapFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class UserEmployee extends AppCompatActivity {
    ArrayAdapter<String> my_adapter;
    ArrayList<String> data2 = new ArrayList<String>();

    private void aLog(){
        Log.d("asdasd", "asdasd");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_user_employee);

        Intent intent = getIntent();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("Tasks", "Retrieved " + scoreList.size() + " tasks");
                } else {
                    Log.d("tasks", "Error: " + e.getMessage());
                }
                for (ParseObject item: scoreList){
                    data2.add(item.getString("title"));
                }

                my_adapter = new ArrayAdapter<String>(getBaseContext(),
                        R.layout.task_list_layout,
                        R.id.task_list_layout_textview,
                        data2);

                ListView task_view = (ListView) findViewById(R.id.task_list_view);
                task_view.setAdapter(my_adapter);
                task_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String task = scoreList.get(position).getString("title");
                        Log.d("bla: ", task + " ");
                        Intent intent1 = new Intent(getBaseContext(), DetailMapView.class);
                        intent1.putExtra("task", task);
                        startActivity(intent1);



                    }
                });
            }
        });


    }
}
