package coms309_29.tapp_5;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date extends AppCompatActivity {

    boolean leaveSet = false;
    boolean returnSet = false;
    static boolean activityCalled = false;

    int btnClicked;

    int lYear;
    int lMonth;
    int lDay;
    int rYear;
    int rMonth;
    int rDay;

    EditText LTxt;
    EditText RTxt;

    Button leaveDate;
    Button returnDate;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        LTxt = (EditText) findViewById(R.id.LeaveDate);
        RTxt = (EditText) findViewById(R.id.ReturnDate);
        leaveDate = (Button) findViewById(R.id.button);
        returnDate = (Button) findViewById(R.id.button2);
        next = (Button) findViewById(R.id.nextBtn);
    }

    public void nextClick(View v) {
        if (!leaveSet && returnSet) {
            Toast.makeText(getApplicationContext(), "Please enter when you are leaving", Toast.LENGTH_SHORT).show();
        } else if (!returnSet && leaveSet) {
            Toast.makeText(getApplicationContext(), "Please enter when you are returning", Toast.LENGTH_SHORT).show();
        } else if (!leaveSet && !returnSet) {
            Toast.makeText(getApplicationContext(), "Please enter when you are leaving and when you are planning on returning", Toast.LENGTH_SHORT).show();
        } else {

            if (!activityCalled) {
                DataHolder.addData("LeaveDate", lMonth + "/" + lDay + "/" + lYear);
                DataHolder.addData("ReturnDate", rMonth + "/" + rDay + "/" + rYear);
            } else {
                DataHolder.updateData(6, "Departure", lMonth + "/" + lDay + "/" + lYear);
                DataHolder.updateData(7, "Arrival", rMonth + "/" + rDay + "/" + rYear);
            }
            Intent intent = new Intent("coms309_29.tapp_5.PointofInterest");
            startActivity(intent);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            btnClicked = 0;
            DialogFragment fragment = new SelectDateFragment();
            fragment.show(getFragmentManager(), "DatePicker");
        } else {
            btnClicked = 1;
            DialogFragment fragment = new SelectDateFragment();
            fragment.show(getFragmentManager(), "DatePicker");
        }
    }

    public void saveDate(int y, int m, int d) {
        if (btnClicked == 0) {
            leaveSet = true;
            LTxt.setText(m + "/" + d + "/" + y);
            lYear = y;
            lMonth = m;
            lDay = d;
        } else if (btnClicked == 1) {
            returnSet = true;
            RTxt.setText(m + "/" + d + "/" + y);
            rYear = y;
            rMonth = m;
            rDay = d;
        }
    }

    //Checks to make sure the selected date is a valid date.
    //Checks if both of the selected dates are after the current date, if not it will tell the user to select a different date.
    //also checks the return date to make sure that it is after the leave date, If not it will tell user to input date that is after leave date.
    public boolean checkDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        if (year < c.get(Calendar.YEAR)) {
            Toast.makeText(getApplicationContext(), "Please enter a date after today", Toast.LENGTH_SHORT).show();
            return false;
        } else if (year == c.get(Calendar.YEAR) && month < c.get(Calendar.MONTH)) {
            Toast.makeText(getApplicationContext(), "Please enter a date after today", Toast.LENGTH_SHORT).show();
            return false;
        } else if (year == c.get(Calendar.YEAR) && month == c.get(Calendar.MONTH) && day < c.get(Calendar.DAY_OF_MONTH)) {
            Toast.makeText(getApplicationContext(), "Please enter a date after today", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (btnClicked == 1) {
            if (year < lYear) {
                Toast.makeText(getApplicationContext(), "Please enter a date after your leave date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (year == lYear && month < lMonth) {
                Toast.makeText(getApplicationContext(), "Please enter a date after your leave date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (year == lYear && month == lMonth && day < lDay) {
                Toast.makeText(getApplicationContext(), "Please enter a date after your leave date", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;

    }

    public String getDepartDate() throws ParseException {
       String strDepartDate = DataHolder.get(6).substring(10);
        String dateDept ;
          SimpleDateFormat newDate = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date myDate = newDate.parse(strDepartDate);
        newDate.applyPattern("yyyy-MM-dd");
        dateDept = newDate.format(myDate);

        return dateDept;
    }

    public String getReturnDate() throws  ParseException{
        String returnD = DataHolder.get(7).substring(11);
        String returnDate;
        SimpleDateFormat newDate =new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date myDate = newDate.parse(returnD);
        newDate.applyPattern("yyyy-MM-dd");
        returnDate =newDate.format(myDate);
        Log.d("returnD1", returnDate);
        return returnDate;
    }
}