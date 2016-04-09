package coms309_29.tapp_5;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
import ch.boye.httpclientandroidlib.util.EntityUtils;

public class GetPlaces {
    static String airport = "";
    static Context con;
    static double lat = 0.0, lng = 0.0;
    static double radius = 10000.0;
    static ArrayList<String> types = new ArrayList<>();
    static List<Place> places;
    static ArrayList<NameValuePair> params = new ArrayList<>();

    private static final String API_KEY = "AIzaSyC9HBiksiYXzKptin67YT_3shdpnLL0o0c";    // Server Key

    // Google Places serach url's
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

    public static void setPlace(Context context, String code) {
        airport = code;
        con = context;
    }

    // Sets the types to be used for the Google Places API request. Splits the given string by "," and then gets rid of any spaces.
    public static void setTypes(String t) {
        int i = 0;
        String[] temp;
        temp = t.split(",");
        while(i < temp.length) {
            temp[i] = temp[i].toLowerCase().trim();
            if(temp[i].equals("points of interest")) {
                types.add("point_of_interest");
            } else if(temp[i].equals("parks & recreation")) {
                types.add("parks");
                types.add("zoo");
                types.add("amusement_park");
            } else {
                types.add(temp[i]);
            }
            i++;
        }
    }

    public static double getLat() {
        return lat;
    }

    public static double getLng() {
        return lng;
    }

    private static void getLatLongFromAddress() {
        Geocoder geoCoder = new Geocoder(con, Locale.getDefault());
        while (lat == 0.0 && lng == 0.0) {
            try {
                List<Address> addresses = geoCoder.getFromLocationName(airport, 1);
                if (addresses.size() > 0) {
                    lat = addresses.get(0).getLatitude();
                    lng = addresses.get(0).getLongitude();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Place> search(String type) {
        final StringBuilder urlString = new StringBuilder(PLACES_SEARCH_URL);
        getLatLongFromAddress();

        urlString.append("&location=" + lat + "," + lng + "&radius=" + radius + "&types=");

        if(type.equals("places")) {
            int i = 0;
            while (i < types.size()) {
                if (i == types.size() - 1) {
                    urlString.append(types.get(i));
                } else {
                    urlString.append(types.get(i) + "%7C");
                }
                i++;
            }
        } else if(type.equals("hotels")) {
            urlString.append("lodging");
        }
        urlString.append("&sensor=false&key=" + API_KEY);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "";
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet get = new HttpGet(urlString.toString());

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.execute(get).getEntity().getContent()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    client.execute(get).getEntity().getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");
                    List<Place> arrayList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            Place place = Place.jsonToPontoReferencia((JSONObject) array.get(i));
                            arrayList.add(place);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    places = arrayList;
                } catch (JSONException ex) {
                    Logger.getLogger(GetPlaces.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return places;
    }

    public static String getAddress(LatLng coor, String name, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = "";
        try {
            List<Address> list = geocoder.getFromLocation(coor.latitude, coor.longitude, 1);
            if(list != null && list.size() > 0) {
                Address address = list.get(0);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    builder.append(address.getAddressLine(i)).append("\n");
                }
                builder.append(address.getLocality()).append("\n");
                builder.append(address.getCountryName());
                result = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        params.clear();
        params.add(new BasicNameValuePair("Name", name));
        params.add(new BasicNameValuePair("Address", result));
        return result;
    }

    static Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("http://proj-309-29.cs.iastate.edu/insertPlaces.php");
                post.setEntity(new UrlEncodedFormEntity(params));
                client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}