package coms309_29.tapp_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

public class Budget extends AppCompatActivity {

    static boolean activityCalled = false;

    String budget = "";
    String numPeople = "";
    Button button;
    private Spinner spinnerPeople;
    private Spinner spinnerBudget;
    private CheckBox chkFamily, chkKids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        addItemsOnPeopletSpinner();
        addItemsOnBudgetSpinner();
        addListenerOnChkFamily();
        addListenerOnChkKids();
        button = (Button) findViewById(R.id.button_budget);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget = budgetFormat(spinnerBudget.getSelectedItem().toString());
                numPeople = spinnerPeople.getSelectedItem().toString();

                addParams();
                Intent intent = new Intent("coms309_29.tapp_5.Date");
                startActivity(intent);
            }
        });
    }

    private void addItemsOnBudgetSpinner() {
        spinnerBudget = (Spinner) findViewById(R.id.budgetSpinner);
        ArrayAdapter<CharSequence> adapterBudget = ArrayAdapter.createFromResource(this,
                R.array.muchMoney, android.R.layout.simple_spinner_item);
        adapterBudget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBudget.setAdapter(adapterBudget);
    }


    public void addItemsOnPeopletSpinner() {
        spinnerPeople = (Spinner) findViewById(R.id.numPeopleSpinner);
        ArrayAdapter<CharSequence> adapterPeople = ArrayAdapter.createFromResource(this,
                R.array.numPeep, android.R.layout.simple_spinner_item);
        adapterPeople.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeople.setAdapter(adapterPeople);

        spinnerPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkIfSingle(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addListenerOnChkFamily() {
        chkFamily = (CheckBox) findViewById(R.id.familyCheckbox);

        chkFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(Budget.this, "Family Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addListenerOnChkKids() {
        chkKids = (CheckBox) findViewById(R.id.kidsCheckbox);

        chkKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(Budget.this, "Ugh, too bad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkIfSingle(int position) {
        if (position == 0) {
            chkFamily.setEnabled(false);
            chkKids.setEnabled(false);
        } else {
            chkFamily.setEnabled(true);
            chkKids.setEnabled(true);
        }
    }

    public String budgetFormat(String b) {
        String intAsString = "";
        int index = 0;
        while (index < b.length()) {
            if (Character.isDigit(b.charAt(index))) {
                intAsString += b.charAt(index);
            }
            index++;
        }
        return intAsString;
    }

    public void addParams() {
        if (!activityCalled) {
            DataHolder.addData("Budget", budget);
            DataHolder.addData("People", numPeople);

            if (chkFamily.isChecked()) {
                DataHolder.addData("Family", "Yes");
            } else {
                DataHolder.addData("Family", "No");
            }

            if (chkKids.isChecked()) {
                DataHolder.addData("Kids", "Yes");
            } else {
                DataHolder.addData("Kids", "No");
            }
        } else {
            DataHolder.updateData(2, "Budget", budget);
            DataHolder.updateData(3, "People", numPeople);

            if (chkFamily.isChecked()) {
                DataHolder.updateData(4, "Family", "Yes");
            } else {
                DataHolder.updateData(4, "Family", "No");
            }


            if (chkKids.isChecked()) {
                DataHolder.updateData(5, "Kids", "Yes");
            } else {
                DataHolder.updateData(5, "Kids", "No");
            }
        }
    }

    public int getNumPeople(){
        return Integer.parseInt(DataHolder.get(3).substring(7));
    }
}