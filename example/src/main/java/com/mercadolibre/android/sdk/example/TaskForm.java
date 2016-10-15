package com.mercadolibre.android.sdk.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class TaskForm extends AppCompatActivity {

    EditText title_edit_text;
    EditText payment_edit_text;
    EditText descrip_edit_text;
    Button submit_btn;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        Intent intent = getIntent();

        title_edit_text = (EditText) findViewById(R.id.title_edit_text);
        payment_edit_text = (EditText) findViewById(R.id.payment_edit_text);
        descrip_edit_text = (EditText) findViewById(R.id.descrip_edit_text);
        submit_btn = (Button) findViewById(R.id.submit_task_btn);

        submit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = title_edit_text.getText().toString();
                String payment = payment_edit_text.getText().toString();
                String description = descrip_edit_text.getText().toString();

                gps = new GPSTracker(TaskForm.this);
                ParseGeoPoint point = null;

                if (gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    point = new ParseGeoPoint(latitude, longitude);
                }

                ParseObject task = new ParseObject("Tasks");
                task.put("title", title);
                task.put("price", Integer.parseInt(payment));
                task.put("description", description);
                if (point != null){
                    task.put("geolocation", point);
                }

                task.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(getBaseContext(), "Se ha publicado con exito!", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else{
                            Log.i("Parse: ", e + "Save Failed");
                        }
                    }
                });
            }
        });







    }
}