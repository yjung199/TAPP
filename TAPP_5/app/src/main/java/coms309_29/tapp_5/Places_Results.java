package coms309_29.tapp_5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Places_Results extends AppCompatActivity {
    private List<Place> places = GetPlaces.search("place");
    private Context con = this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for(int i = 0; i < places.size(); i++) {
            GetPlaces.getAddress(new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude()), places.get(i).getName(), con);
            GetPlaces.t.run();
            try {
                GetPlaces.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places__results);
        setTitle("Places");
        ListView lv = (ListView) findViewById(R.id.placesList);
        String[] names = new String[places.size()];

        for(int i = 0; i < places.size(); i++) {
            names[i] = places.get(i).getName();
        }

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("coms309_29.tapp_5.MapActivity");
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
