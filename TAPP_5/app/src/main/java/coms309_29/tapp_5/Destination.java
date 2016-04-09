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

public class Destination extends AppCompatActivity {

    static boolean activityCalled = false;
    Button button2;
    String[] nextLine;
    String strDestin = "";
    ArrayAdapter<String> adapter;
    private EditText txtDestinCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        setTitle("Destination");
        Log.d("Notice", DataHolder.get(0));
        txtDestinCity = (EditText) findViewById(R.id.destinCityAutoComp);
        button2 = (Button) findViewById(R.id.button_destin);
        button2.setOnClickListener(new View.OnClickListener() {
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
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.destinCityAutoComp);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, std);
        autoCompleteTextView.setAdapter(adapter);
    }


    private void checkEmpty() {
        strDestin = txtDestinCity.getText().toString();

        if (strDestin.equals("")) {
            txtDestinCity.setError("Please fill where your trip goes!");
        } else {
            if (!activityCalled) {
                DataHolder.addData("Destination", strDestin.substring(strDestin.length() - 3));
            }
            else {
                DataHolder.updateData(1, "Destination", strDestin.substring(strDestin.length() - 3));
            }
            Intent intent = new Intent("coms309_29.tapp_5.Budget");
            activityCalled = true;
            startActivity(intent);
        }
    }

    public String getStrDestin(){
        String dest = DataHolder.get(1).substring(12);
        Log.d("Dest", dest);
        return dest;
    }
    public String getStrOrgin(){
        String origin = DataHolder.get(0).substring(7);
        Log.d("Orign", origin);
        return origin;
    }


}