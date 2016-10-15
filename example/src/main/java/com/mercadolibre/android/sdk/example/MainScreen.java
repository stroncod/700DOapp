package com.mercadolibre.android.sdk.example;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.mercadolibre.android.sdk.ApiResponse;
import com.mercadolibre.android.sdk.Identity;
import com.mercadolibre.android.sdk.Meli;

/**
 * This is the home screen for the example application. In it, you will find all you
 * need to start integrating the MercadoLibre android SDK into your project.
 */
public class MainScreen extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        // prepare the UI.
        setupUi();

        // Set SDK to log events
        Meli.setLoggingEnabled(true);

        // Initialize the MercadoLibre SDK
        Meli.initializeSDK(getApplicationContext());

    }


    private void setupUi() {

        Toolbar mainScreenToolbar = (Toolbar) findViewById(R.id.main_screen_toolbar);
        setSupportActionBar(mainScreenToolbar);

        Button btnLogin = (Button) findViewById(R.id.btn_login_example);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_login_example:
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }


    private String getUserID() {
        Identity identity = Meli.getCurrentIdentity(getApplicationContext());
        if (identity == null) {
            return null;
        } else {
            return identity.getUserId();
        }
    }

    private class GetAsycTask extends AsyncTask<Command, Void, ApiResponse> {

        @Override
        protected void onPreExecute() {
            findViewById(R.id.pg_loading).setVisibility(View.VISIBLE);
        }


        @Override
        protected ApiResponse doInBackground(Command... params) {
            return params[0].executeCommand();
        }
    }


    private abstract class Command {
        abstract ApiResponse executeCommand();
    }
}


