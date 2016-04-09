package coms309_29.tapp_5.arrivalDepartureCSV;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
import coms309_29.tapp_5.DataHolder;
import coms309_29.tapp_5.R;

public class ArrivalTest extends AppCompatActivity {

    // This is git status checking
    String state = "";
    Button button1;
    String[] nextLine;
    ArrayAdapter<String> adapter;
//    private static final String[] ct;

    private AutoCompleteTextView autoCompleteTextView;
    private EditText txtOriginCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_test);

        txtOriginCity = (EditText) findViewById(R.id.originCityAutoComp1);
        button1 = (Button) findViewById(R.id.button_origin1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();

            }
        });


        try {
            readCSV();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IOEXCEPTION", "exception has been caught");
        }


    }

    public void readCSV() throws IOException {
        InputStream is = this.getAssets().open("airport-codes.csv");
        InputStreamReader ifr = new InputStreamReader(is, "UTF-8");

        CSVReader reader = new CSVReader (ifr);
        ArrayList<String> std = new ArrayList<>();

        while ((nextLine = reader.readNext()) != null)
        {
//            nextLine = reader.readNext();
            Log.i("ArrivalTest", nextLine[0] + "- " + nextLine[1]);
            std.add(nextLine[0] + " - " + nextLine[1]);


        }
        reader.close();
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.originCityAutoComp1);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, std);
        autoCompleteTextView.setAdapter(adapter);
    }


    private void checkEmpty() {
        String strOrigin = txtOriginCity.getText().toString();

        if (strOrigin.equals("")) {
            txtOriginCity.setError("Please fill where your trip starts!");
        } else {

//            params.add(new BasicNameValuePair("Origin", strOrigin + ", " + state));
            DataHolder.addData("Origin", strOrigin.substring(strOrigin.length()-3) + ", " + state);
            Intent intent = new Intent("coms309_29.tapp_5.Destination");
//            intent.putExtra("params", params);
            startActivity(intent);
        }
    }


}