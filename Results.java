package coms309_29.tapp_5;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

/**
 * This class calls the flights Api and sends back the information on the flights back to the use.
 * It takes destination, origin.
 * Other information that can be inputted are the number of people and date of travel.
 *
 * @author G Ndemi
 */

public class Results extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText editTextOrigin;
        EditText editTextArrival;
        EditText editTextDestination;
        EditText editTextFlightNum;
        EditText editTextPrice;
        EditText flightCarrier;
        EditText departureTime;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_results);

        //name that the app uses to access the APi
        final String APPLICATION_NAME = "myApp";

        final String API_KEY = "AIzaSyDV5o660GuzFXZL1tc3qQLK7C6unOVbG8A";

        //uses these classes to access the destination and origin.
        Destination des = new Destination();
        MainActivity main = new MainActivity();


        /** Global instance of the HTTP transport. */
        HttpTransport httpTransport;


        /** Global instance of the JSON factory. */
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        try {

            httpTransport = AndroidHttp.newCompatibleTransport();
            PassengerCounts passengers = new PassengerCounts();
            passengers.setAdultCount(1);
            List<SliceInput> slices = new ArrayList<SliceInput>();
            SliceInput slice = new SliceInput();
            //String origin = main.getOrigin();
            String origin = "DSM";
            String destination = "NBO";


            slice.setOrigin(origin);
            slice.setDestination(destination);
            slice.setDate("2015-11-19");
            slices.add(slice);
            TripOptionsRequest request = new TripOptionsRequest();
            request.setSolutions(1);
            request.setPassengers(passengers);
            request.setSlice(slices);
            TripsSearchRequest parameters = new TripsSearchRequest();
            parameters.setRequest(request);
            QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME).setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();
            //This is the where the API is called.
            TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();

            //TripResults is the list of the flights information received from QPX api.
            List<TripOption> tripResults = list.getTrips().getTripOption();

            editTextArrival = (EditText) findViewById(R.id.arrivalTime);
            editTextOrigin = (EditText) findViewById(R.id.ediTextOrigin);
            flightCarrier = (EditText) findViewById(R.id.flightCarrier);
            editTextDestination = (EditText) findViewById(R.id.ediTextDestination);
            departureTime = (EditText) findViewById(R.id.departureTime);
            editTextFlightNum = (EditText) findViewById(R.id.ediTextFlightNum);
            editTextPrice = (EditText) findViewById(R.id.ediTextPrice);
            editTextDestination.setText(slice.getDestination());
            editTextOrigin.setText(slice.getOrigin());


            for (int i = 0; i < tripResults.size(); i++) {


                //Slice
                List<SliceInfo> sliceInfo = tripResults.get(i).getSlice();
                for (int j = 0; j < sliceInfo.size(); j++) {
                    List<SegmentInfo> segInfo = sliceInfo.get(j).getSegment();

                    for (int k = 0; k < segInfo.size(); k++) {
                        FlightInfo flightInfo = segInfo.get(k).getFlight();
                        editTextFlightNum.setText(flightInfo.getNumber());
                        flightCarrier.setText(flightInfo.getCarrier());

                        List<LegInfo> leg = segInfo.get(k).getLeg();
                        for (int l = 0; l < leg.size(); l++) {
                            String departTime = leg.get(l).getDepartureTime();
                            String arrivalTime = leg.get(l).getArrivalTime();
                            editTextArrival.setText(arrivalTime.substring(11));
                            departureTime.setText(departTime.substring(11));


                        }
                    }
                }

                //Pricing
                List<PricingInfo> priceInfo = tripResults.get(i).getPricing();
                for (int p = 0; p < priceInfo.size(); p++) {
                    editTextPrice.setText(priceInfo.get(p).getSaleTotal());
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        // System.exit(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
}
