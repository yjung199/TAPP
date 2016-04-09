package coms309_29.tapp_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapActivity extends AppCompatActivity {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        double latitude = getIntent().getDoubleExtra("lat", 0.0);
        double longitude = getIntent().getDoubleExtra("long", 0.0);
        String name = getIntent().getStringExtra("name");
        setTitle("Map - " + name);

        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            LatLng marker = new LatLng(latitude, longitude);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.addMarker(new MarkerOptions().position(marker).title(name));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
