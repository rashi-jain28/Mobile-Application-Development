package com.example.rashi.googlemaps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<AppResponse.Points> points;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Paths Activity");
        points= readFileFromRawDirectory(R.raw.trip);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;
        PolylineOptions lineOptions = new PolylineOptions();
        ArrayList<LatLng> pt = new ArrayList<>();
        LatLngBounds.Builder mapBounds = new LatLngBounds.Builder();
        for (int z = 0; z <= points.size()-1; z++) {

            LatLng pos = new LatLng(points.get(z).getLatitude(), points.get(z).getLongitude());
            pt.add(pos);
            mapBounds.include(pos);
            if (z == 0) {
                mMap.addMarker(new MarkerOptions()
                        .position(pos));
            }
            if (z == points.size() - 1) {
                mMap.addMarker(new MarkerOptions()
                        .position(pos));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
            mMap.animateCamera(zoom);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds.build(),getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels,2));
            lineOptions.addAll(pt);
            lineOptions.width(10);
            lineOptions.color(Color.RED);
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }

           // mMap.addMarker(new MarkerOptions().position(points[z]).title("Marker in Sydney")

    }
    private ArrayList<AppResponse.Points> readFileFromRawDirectory(int resourceId){
        InputStream iStream = getApplicationContext().getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteStream = null;
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(iStream);
        AppResponse appResponse = gson.fromJson(reader, AppResponse.class);
        points = appResponse.points;
        //Log.d("demo",""+appResponse );
        return points;
    }
}


