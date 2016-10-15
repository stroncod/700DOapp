package com.mercadolibre.android.sdk.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserEmployer extends AppCompatActivity {

    Button create_task;
    Button list_user_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_employer);

        create_task = (Button) findViewById(R.id.task_create);
        list_user_task = (Button) findViewById(R.id.user_task_view);

        create_task.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), TaskForm.class);
                startActivity(intent);

            }
        });
    }
}
