package com.mercadolibre.android.sdk.example;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mercadolibre.android.sdk.Identity;
import com.mercadolibre.android.sdk.Meli;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {


    // Request code used to receive callbacks from the SDK
    private static final int REQUEST_CODE = 999;
    private String urlculia;
    private String f_name;
    private String l_name;
    private String email;
    private String idCulia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

            // ask the SDK to start the login process
            Meli.startLogin(this, REQUEST_CODE);

        //new GetinfoJson().execute();

        //ParseObject newUser = new ParseObject("Usuarios");
        //
        //newUser.put("meliID",idCulia);
        //newUser.put("firstName",f_name);
        //newUser.put("lastName",l_name);
        //newUser.put("email",email);
        //
        //newUser.saveInBackground(new SaveCallback() {
            //public void done(ParseException e) {
                //if (e == null) {
                    //Log.i("Parse", "Save Succeeded");
                //} else {
                    //Log.i("Parse", "Save Failed");
                //}
            //}
        //});
        ParseObject newUser = new ParseObject("Usuarios");

        newUser.put("meliID",idCulia);
        newUser.put("firstName",f_name);
        newUser.put("lastName",l_name);
        newUser.put("email",email);

        newUser.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Parse", "Save Succeeded");
                } else {
                    Log.i("Parse", "Save Failed");
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                processLoginProcessCompleted();
            } else {
                processLoginProcessWithError();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processLoginProcessCompleted() {
        Identity identity = Meli.getCurrentIdentity(getApplicationContext());
        if (identity != null) {
            idCulia = identity.getUserId();
            urlculia = "https://api.mercadolibre.com/users/"+identity.getUserId()+"?access_token="+identity.getAccessToken().getAccessTokenValue();
            new GetinfoJson().execute();
            //((TextView) findViewById(R.id.txt_user_id)).setText(getString(R.string.user_id_text, identity.getUserId()));
            //((TextView) findViewById(R.id.txt_access_token)).setText(getString(R.string.access_token_text, identity.getAccessToken().getAccessTokenValue()));
            //((TextView) findViewById(R.id.txt_expires_in)).setText(getString(R.string.expires_in_text, String.valueOf(identity.getAccessToken().getAccessTokenLifetime())));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }
    }
    private void processLoginProcessWithError() {
        Toast.makeText(LoginScreen.this, "Oooops, something went wrong with the login process", Toast.LENGTH_SHORT).show();
    }
    private class GetinfoJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0){
            //Log.d("url",urlculia);
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlculia);

            if (jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    f_name = jsonObj.getString("first_name");
                    l_name = jsonObj.getString("last_name");
                    email = jsonObj.getString("email");

                }catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("fname",f_name);
            Log.d("idcu",idCulia);


        }

    }
}
