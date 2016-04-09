package coms309_29.tapp_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.boye.httpclientandroidlib.NameValuePair;

public class MainActivity extends AppCompatActivity {

    static boolean activityCalled = false;
    Button button1;
    String[] nextLine;
    ArrayAdapter<String> adapter;
    private EditText txtOriginCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TAPP");

        txtOriginCity = (EditText) findViewById(R.id.originCityAutoComp);
        button1 = (Button) findViewById(R.id.button_origin);

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
            Log.d("IOException", "exception has been caught");
        }


    }

    public void readCSV() throws IOException {
        AutoCompleteTextView autoCompleteTextView;
        InputStream is = this.getAssets().open("airport-codes.csv");
        InputStreamReader ifr = new InputStreamReader(is, "UTF-8");

        CSVReader reader = new CSVReader(ifr);
        ArrayList<String> std = new ArrayList<>();

        while ((nextLine = reader.readNext()) != null) {
            std.add(nextLine[0] + " - " + nextLine[1]);
        }
        reader.close();
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.originCityAutoComp);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, std);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void checkEmpty() {
        String strOrigin = txtOriginCity.getText().toString();

        if (strOrigin.equals("")) {
            txtOriginCity.setError("Please fill where your trip starts!");
        } else {
            if (!activityCalled) {
                DataHolder.addData("Origin", strOrigin.substring(strOrigin.length() - 3));
            }
            else
            {
                DataHolder.updateData(0, "Origin", strOrigin.substring(strOrigin.length() - 3));
            }
            Intent intent = new Intent("coms309_29.tapp_5.Destination");
            activityCalled = true;
            startActivity(intent);
        }
    }
}