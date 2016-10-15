package com.mercadolibre.android.sdk.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailMapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    LatLng marker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            mMap = googleMap;

            // Android version is lesser than 6.0 or the permission is already granted.
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // if (locationGPS != null) {
            //    marker = new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());
            //} else {
            //   marker = new LatLng(locationNet.getLatitude(), locationNet.getLongitude());
            // }
            Intent intent = getIntent();

            String data =  intent.getStringExtra("task");
            String task_detail = intent.getStringExtra("task");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
            query.whereEqualTo("title", task_detail);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> object, ParseException e) {
                    ArrayList<String> aux = new ArrayList<String>();

                    if (e == null) {

                        String id = object.get(0).getObjectId();
                        Log.d("id", id);

                        ParseGeoPoint point = (ParseGeoPoint) object.get(0).getParseGeoPoint("geolocation");


                        marker = new LatLng(point.getLatitude(), point.getLongitude());


                        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                        Location location = new Location("Aqui estoy");

                        location.setLatitude(marker.latitude);
                        location.setLongitude(marker.longitude);
                        int precio = (int) object.get(0).getNumber("price");

                        mMap.addMarker(new MarkerOptions().position(marker)
                                .snippet("Pago: $"+Integer.toString(precio)+" CLP"))
                                .setTitle(object.get(0).getString("title"));
                        TextView btn = (TextView) findViewById(R.id.descr_task_map);
                        btn.setText(object.get(0).getString("description"));

                    } else {
                        Log.d("Detail: ", e + " Happened");
                    }

                }
            });









            // Add a marker in Sydney and move the camera
            // LatLng sydney = new LatLng(-34, 151);
            // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DetailMapView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
