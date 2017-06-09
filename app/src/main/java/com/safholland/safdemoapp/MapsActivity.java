package com.safholland.safdemoapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String url = "http://api.safholland.us/v3/gqss?uom=metric&type=ss";
    private String urlGR = "http://api.safholland.us/v3/gqss?uom=imperial&type=ss&origLat=42.9633599&origLng=-85.66808630000003&origAddress=grand+rapids%2C+mi&formattedAddress=Grand+Rapids%" +
            "2C+MI%2C+USA&boundsNorthEast=%7B%22lat%22%3A43.0290509%2C%22lng%22%3A-85.56864589999998%7D&boundsSouthWest=%7B%22lat%22%3A42.8836659%2C%22lng%22%3A-85.75153209999996%7D";
    private JSONArray serviceStations = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void fetchServiceStations() {

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
            (Request.Method.GET, urlGR, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("Response", response.toString());
                    serviceStations = response;
                    updateMapWithServiceLocations(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley Error", error.toString());

                }
            });
        Volley.newRequestQueue(this).add(jsObjRequest);
    }

    public void updateMapWithServiceLocations(JSONArray stations) {
        Log.d("Updating Map:", stations.toString());


        for (int i = 0; i < stations.length(); i++) {
            try {
                JSONObject station = stations.getJSONObject(i);

                LatLng serviceLocation = new LatLng(Double.parseDouble(station.getString("lat")), Double.parseDouble(station.getString("lng")));
                mMap.addMarker(new MarkerOptions().position(serviceLocation).title(station.getString("Title")));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(serviceLocation));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        Add Locations to map
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        fetchServiceStations();
    }
}
