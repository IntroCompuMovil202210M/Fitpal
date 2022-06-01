package com.example.parcialfinal;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.parcialfinal.databinding.ActivityMapaParcialBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapaParcial extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapaParcialBinding binding;
    private Marker currentLocation;
    private Marker localizaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaParcialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(40.6,  -73.9);
         mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
         mMap.addMarker(new MarkerOptions().position(sydney).title("Aqui Estamos"));

         //Gestos
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        //Se mueve la camara al marcador de referencia
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));






        cargarLocalizaciones();

    }

    private void cargarLocalizaciones(){
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray locationsJsonArray = json.getJSONArray("locations");
            for (int i = 0; i < locationsJsonArray.length(); i++) {
                JSONObject jsonObject = locationsJsonArray.getJSONObject(i);
                Double lat = jsonObject.getDouble("latitude");
                Double longi = jsonObject.getDouble("longitude");

                LatLng pos = new LatLng(lat,longi);
                localizaciones = mMap.addMarker(new MarkerOptions().position(pos).title(jsonObject.getString("name")));
                Log.e("hola",pos.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try{
            InputStream is= this.getAssets().open("locations.json");
            int size = is.available();
            byte[] buffer= new byte[size];
            is.read(buffer);
            is.close();
            json= new String(buffer,"UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }


        return json;
    }

}