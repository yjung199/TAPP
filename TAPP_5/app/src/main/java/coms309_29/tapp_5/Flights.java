package coms309_29.tapp_5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

/**
 * This class calls the flights Api and sends back the information on the flights back to the use.
 * It takes destination, origin.
 * Other information that can be inputted are the number of people and date of travel.
 *
 * @author G Ndemi
 */

public class Flights extends AppCompatActivity implements View.OnClickListener {
    String origin, destination, departDate, departTime, arrivalTime, flightNum, carrier, price, returnDate;
    InputStream is = null;
    EditText editTextOrigin, editTextArrival, editTextDestination, editDepartDate, editTextFlightNum, editTextPrice, flightCarrier, departureTime;
    List<TripOption> tripResults;
    TripsSearchResponse list;
    Button btnNext, btnPrev, btnSave, results;
    Date date = new Date();
    private int track = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_flights);
        final String APPLICATION_NAME = "myApp2";
        final String API_KEY = "AIzaSyDfxBRnKElIKyJnSTcUijnszuMqBvYoh0M";
        Destination des = new Destination();
        Budget budget = new Budget();

        /** Global instance of the HTTP transport. */
        HttpTransport httpTransport;
        /** Global instance of the JSON factory. */
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        try {
            httpTransport = AndroidHttp.newCompatibleTransport();
            PassengerCounts passengers = new PassengerCounts();
            //num is the number of passengers the client entered
            int num = budget.getNumPeople();
            passengers.setAdultCount(num);
            List<SliceInput> slices = new ArrayList<>();
            SliceInput slice = new SliceInput();
            destination = des.getStrDestin();
            origin = des.getStrOrgin();
            slice.setOrigin(origin);
            slice.setDestination(destination);
            departDate = date.getDepartDate();
            slice.setDate(departDate);
            slices.add(slice);
            TripOptionsRequest request = new TripOptionsRequest();
            //setSolutions is the number of flights solutions that will be displayed. Maximum is 500.
            request.setSolutions(6);
            request.setPassengers(passengers);
            request.setSlice(slices);
            TripsSearchRequest parameters = new TripsSearchRequest();
            parameters.setRequest(request);
            QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME).setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();
            //This is the where the API is called.
            list = qpXExpress.trips().search(parameters).execute();
            editTextArrival = (EditText) findViewById(R.id.arrivalTime);
            editTextOrigin = (EditText) findViewById(R.id.ediTextOrigin);
            flightCarrier = (EditText) findViewById(R.id.flightCarrier);
            editTextDestination = (EditText) findViewById(R.id.ediTextDestination);
            editDepartDate = (EditText) findViewById(R.id.editdepartDate);
            departureTime = (EditText) findViewById(R.id.departureTime);
            editTextFlightNum = (EditText) findViewById(R.id.ediTextFlightNum);
            editTextPrice = (EditText) findViewById(R.id.ediTextPrice);
            editTextDestination.setText(slice.getDestination());
            editTextOrigin.setText(slice.getOrigin());
            editDepartDate.setText(slice.getDate());
            btnNext = (Button) findViewById(R.id.button_next);
            btnNext.setOnClickListener(this);
            btnPrev = (Button) findViewById(R.id.button_Prev);
            btnPrev.setOnClickListener(this);
            showData();
            btnSave = (Button) findViewById(R.id.button_result);
            btnSave.setOnClickListener(this);
            results = (Button) findViewById(R.id.results);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * This method shows the flights information retrieved from the flights API.c
     * Calling this method shows all the information the client requested.
     */
    private void showData() {
        //TripResults is the list of the flights information received from QPX api.
        tripResults = list.getTrips().getTripOption();
        //Slice
        List<SliceInfo> sliceInfo = tripResults.get(track).getSlice();
        for (int j = 0; j < sliceInfo.size(); j++) {

            List<SegmentInfo> segInfo = sliceInfo.get(j).getSegment();
            for (int k = 0; k < segInfo.size(); k++) {
                FlightInfo flightInfo = segInfo.get(k).getFlight();
                flightNum = flightInfo.getNumber();
                editTextFlightNum.setText(flightNum);
                carrier = flightInfo.getCarrier();
                flightCarrier.setText(carrier);

                List<LegInfo> leg = segInfo.get(k).getLeg();
                for (int l = 0; l < leg.size(); l++) {
                    departTime = leg.get(l).getDepartureTime().substring(11);
                    arrivalTime = leg.get(l).getArrivalTime().substring(11);
                    editTextArrival.setText(arrivalTime);
                    departureTime.setText(departTime);
                }
            }
        }
        //This retrieves the The total cost of the flights with taxes. It is in the currency of the clients country of origin.

        List<PricingInfo> priceInfo = tripResults.get(track).getPricing();
        for (int p = 0; p < priceInfo.size(); p++) {
            price = priceInfo.get(p).getSaleTotal();
            editTextPrice.setText(price);
        }
    }

    /**
     * At the click of the save button, the data will be inserted in to
     * flights table in mysql database.
     **/
    private void insert() throws ParseException {
        origin = editTextOrigin.getText().toString();
        destination = editTextDestination.getText().toString();
        departDate = editDepartDate.getText().toString();
        departTime = departureTime.getText().toString();
        arrivalTime = editTextArrival.getText().toString();
        flightNum = editTextFlightNum.getText().toString();
        carrier = flightCarrier.getText().toString();
        price = editTextPrice.getText().toString();
        returnDate = date.getReturnDate();
        insertToDatabase(origin, destination, departDate, departTime, arrivalTime, flightNum, carrier, price, returnDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flights, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is the helper method for inserting in to the flights table
     *
     * @param origin      clients flight origin
     * @param destination clients destination
     * @param departDate  departure date
     * @param departTime  flight departure time
     * @param arrivalTime expected flight arrival
     * @param flightNum   flight number
     * @param carrier     flight carrier
     * @param price       cost of the flight before tax in the origins currency
     */

    private void insertToDatabase(String origin, String destination, String departDate, String departTime, String arrivalTime, String flightNum, String carrier, String price, String returnDate) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... flights) {
                String origin = flights[0];
                String destination = flights[1];
                String departDate = flights[2];
                String departTime = flights[3];
                String arrivalTime = flights[4];
                String flightNum = flights[5];
                String carrier = flights[6];
                String price = flights[7];
                String returnDate = flights[8];
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("origin", origin));
                nameValuePairs.add(new BasicNameValuePair("destination", destination));
                nameValuePairs.add(new BasicNameValuePair("departDate", departDate));
                nameValuePairs.add(new BasicNameValuePair("departTime", departTime));
                nameValuePairs.add(new BasicNameValuePair("arrivalTime", arrivalTime));
                nameValuePairs.add(new BasicNameValuePair("flightNum", flightNum));
                nameValuePairs.add(new BasicNameValuePair("carrier", carrier));
                nameValuePairs.add(new BasicNameValuePair("price", price));
                nameValuePairs.add(new BasicNameValuePair("returnDate", returnDate));

                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpPost post = new HttpPost("http://proj-309-29.cs.iastate.edu/insertFlight.php");
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "successes";
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(origin, destination, departDate, departTime, arrivalTime, flightNum, carrier, price, returnDate);
    }

    /*
        This helper method to move through the array of the flights results
     */
    private void moveNext() {
        if (track < tripResults.size() - 1) {
            track++;
            showData();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnNext) {
            moveNext();
        }
        if (v == btnPrev) {
            movePrev();
        }
        if (v == btnSave) {
            try {
                Snackbar.make(v, "Flight info saved!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                insert();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.results) {
            Intent intent = new Intent("coms309_29.tapp_5.Results_Rex");
            startActivity(intent);
        }
    }

    /**
     * This helper method to move to the previous result about the flights
     */
    private void movePrev() {
        if (track > 0) {
            track--;
            showData();
        }
    }
}

