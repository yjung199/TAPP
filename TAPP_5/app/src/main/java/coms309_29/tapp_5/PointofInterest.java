package coms309_29.tapp_5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.ClientProtocolException;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;
import ch.boye.httpclientandroidlib.util.EntityUtils;

public class PointofInterest extends AppCompatActivity {
    private RecAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointof_interest);
        List<Information> information;
        Button btnSelection;
        btnSelection = (Button) findViewById(R.id.button_POI);
        information = new ArrayList<>();
        information.add(new Information("Bar", "It is dummy description for Bar", false, R.drawable.ic_local_bar_black_36dp));
        information.add(new Information("Food", "It is dummy description for Food", false, R.drawable.ic_restaurant_menu_black_36dp));
        information.add(new Information("Points of Interest", "It is dummy description for Points of Interest", false, R.drawable.ic_nature_people_black_36dp));
        information.add(new Information("Museum", "It is dummy description for Museum", false, R.drawable.ic_account_balance_black_36dp));
        information.add(new Information("Theatre", "It is dummy description for theatre", false, R.drawable.ic_event_seat_black_36dp));
        information.add(new Information("Parks & Recreation", "It is dummy description for Parks & Recreation", false, R.drawable.ic_directions_bike_black_36dp));
        RecyclerView recList = (RecyclerView) findViewById(R.id.drawerList);
        recList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recList.setLayoutManager(linearLayoutManager);

        adapter = new RecAdapter(information);
        recList.setAdapter(adapter);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                String POIs = "";
                List<Information> infoList = adapter.getInfoist();

                //Creates a String list of all the selected POIs
                for (int i = 0; i < infoList.size(); i++) {
                    if (infoList.get(i).isSelected()) {
                        POIs = POIs + infoList.get(i).getTitle() + ", ";
                    }
                }

                //Gets rid of the extra comma on the end of the POI list
                if (POIs.endsWith(", ")) {
                    POIs = POIs.substring(0, POIs.length() - 2);
                }

                DataHolder.addData("POIs", POIs);
                new MySQLConnect().execute();

                int i = 0;
                while (i < DataHolder.getSize()) {
                    Log.d("Param", DataHolder.get(i));
                    i++;
                }

                String[] split = DataHolder.get(1).split("=");
                String airport = split[1] + " airport";
                GetPlaces.setPlace(getApplicationContext(), airport);
                GetPlaces.setTypes(POIs);
                Log.d("Coordinates", "Latitute: " + GetPlaces.getLat());
                Log.d("Coordinates", "Longitute: " + GetPlaces.getLng());
                Intent intent = new Intent("coms309_29.tapp_5.Flights");
                startActivity(intent);
            }
        });
    }

    class MySQLConnect extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... v) {
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("http://proj-309-29.cs.iastate.edu/insertP.php");
                post.setEntity(new UrlEncodedFormEntity(DataHolder.getParams()));
                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}