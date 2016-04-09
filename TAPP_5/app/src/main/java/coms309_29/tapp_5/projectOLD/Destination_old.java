//package coms309_29.tapp_5.projectOLD;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//import ch.boye.httpclientandroidlib.NameValuePair;
//import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
//import coms309_29.tapp_5.DataHolder;
//import coms309_29.tapp_5.R;
//
///**
// * Created by young on 11/8/2015.
// */
//public class Destination_old extends AppCompatActivity {
//
//    String state = null;
//    ArrayList<NameValuePair> params = null;
//    Button button2;
//    private Spinner spinner2;
//    private AutoCompleteTextView autoCompleteTextView_Dest;
//    private EditText txtDestinOriginCity;
//    String city_lists[];
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_destination);
////        params = (ArrayList<NameValuePair>) getIntent().getSerializableExtra("params");
//        Log.d("Notice", DataHolder.get(0));
//
//
//        txtDestinOriginCity = (EditText) findViewById(R.id.destinCityAutoComp);
//        addItemsOnSpinner2();
//
//
//
//        button2 = (Button) findViewById(R.id.button_destin);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                checkEmpty();
//            }
//        });
//    }
//
//
//
//
//
//    public void addItemsOnSpinner2() {
//        spinner2 = (Spinner) findViewById(R.id.spinner_dest);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.list_states, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(adapter);
//
//
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_SHORT).show();
//                state = parent.getItemAtPosition(position).toString();
//                txtDestinOriginCity.setText("");
//
//                switch (position) {
//
//                    case 0:
//                        city_lists = getResources().getStringArray(R.array.cities_ALABA);
//                        break;
//                    case 1:
//                        city_lists = getResources().getStringArray(R.array.cities_ALAS);
//                        break;
//                    case 2:
//                        city_lists = getResources().getStringArray(R.array.cities_ARIZ);
//                        break;
//                    case 3:
//                        city_lists = getResources().getStringArray(R.array.cities_ARKA);
//                        break;
//                    case 4:
//                        city_lists = getResources().getStringArray(R.array.cities_CALI);
//                        break;
//                    case 5:
//                        city_lists = getResources().getStringArray(R.array.cities_COLO);
//                        break;
//                    case 6:
//                        city_lists = getResources().getStringArray(R.array.cities_CONNEC);
//                        break;
//                    case 7:
//                        city_lists = getResources().getStringArray(R.array.cities_DELA);
//                        break;
//                    case 8:
//                        city_lists = getResources().getStringArray(R.array.cities_FLORIDA);
//                        break;
//                    case 9:
//                        city_lists = getResources().getStringArray(R.array.cities_GEOR);
//                        break;
//                    case 10:
//                        city_lists = getResources().getStringArray(R.array.cities_HAWA);
//                        break;
//                    case 11:
//                        city_lists = getResources().getStringArray(R.array.cities_IDAH);
//                        break;
//                    case 12:
//                        city_lists = getResources().getStringArray(R.array.cities_ILLIN);
//                        break;
//                    case 13:
//                        city_lists = getResources().getStringArray(R.array.cities_INDIANA);
//                        break;
//                    case 14:
//                        city_lists = getResources().getStringArray(R.array.cities_IOWA);
//                        break;
//                    case 15:
//                        city_lists = getResources().getStringArray(R.array.cities_KANS);
//                        break;
//                    case 16:
//                        city_lists = getResources().getStringArray(R.array.cities_KENTUC);
//                        break;
//                    case 17:
//                        city_lists = getResources().getStringArray(R.array.cities_LOUIS);
//                        break;
//                    case 18:
//                        city_lists = getResources().getStringArray(R.array.cities_MAINE);
//                        break;
//                    case 19:
//                        city_lists = getResources().getStringArray(R.array.cities_MARYLAND);
//                        break;
//                    case 20:
//                        city_lists = getResources().getStringArray(R.array.cities_MASSAC);
//                        break;
//                    case 21:
//                        city_lists = getResources().getStringArray(R.array.cities_MICHIG);
//                        break;
//                    case 22:
//                        city_lists = getResources().getStringArray(R.array.cities_MINNE);
//                        break;
//                    case 23:
//                        city_lists = getResources().getStringArray(R.array.cities_MISSI);
//                        break;
//                    case 24:
//                        city_lists = getResources().getStringArray(R.array.cities_MISSO);
//                        break;
//                    case 25:
//                        city_lists = getResources().getStringArray(R.array.cities_MONTA);
//                        break;
//                    case 26:
//                        city_lists = getResources().getStringArray(R.array.cities_NEBRAS);
//                        break;
//                    case 27:
//                        city_lists = getResources().getStringArray(R.array.cities_NEVADA);
//                        break;
//                    case 28:
//                        city_lists = getResources().getStringArray(R.array.cities_NEWHAMP);
//                        break;
//                    case 29:
//                        city_lists = getResources().getStringArray(R.array.cities_NEWJER);
//                        break;
//                    case 30:
//                        city_lists = getResources().getStringArray(R.array.cities_NEWMEXICO);
//                        break;
//                    case 31:
//                        city_lists = getResources().getStringArray(R.array.cities_NEWYORK);
//                        break;
//                    case 32:
//                        city_lists = getResources().getStringArray(R.array.cities_NORTHCAROL);
//                        break;
//                    case 33:
//                        city_lists = getResources().getStringArray(R.array.cities_NORTHDAKOTA);
//                        break;
//                    case 34:
//                        city_lists = getResources().getStringArray(R.array.cities_OHIO);
//                        break;
//                    case 35:
//                        city_lists = getResources().getStringArray(R.array.cities_OKLAHMOA);
//                        break;
//                    case 36:
//                        city_lists = getResources().getStringArray(R.array.cities_OREGON);
//                        break;
//                    case 37:
//                        city_lists = getResources().getStringArray(R.array.cities_PENNS);
//                        break;
//                    case 38:
//                        city_lists = getResources().getStringArray(R.array.cities_RHODEIS);
//                        break;
//                    case 39:
//                        city_lists = getResources().getStringArray(R.array.cities_SOUTHCAROL);
//                        break;
//                    case 40:
//                        city_lists = getResources().getStringArray(R.array.cities_SOUTHDAKOTA);
//                        break;
//                    case 41:
//                        city_lists = getResources().getStringArray(R.array.cities_TENNESS);
//                        break;
//                    case 42:
//                        city_lists = getResources().getStringArray(R.array.cities_TEXAS);
//                        break;
//                    case 43:
//                        city_lists = getResources().getStringArray(R.array.cities_UTAH);
//                        break;
//                    case 44:
//                        city_lists = getResources().getStringArray(R.array.cities_VERMONT);
//                        break;
//                    case 45:
//                        city_lists = getResources().getStringArray(R.array.cities_VIRGINIA);
//                        break;
//                    case 46:
//                        city_lists = getResources().getStringArray(R.array.cities_WASHINGTON);
//                        break;
//                    case 47:
//                        city_lists = getResources().getStringArray(R.array.cities_WESTVIRGINIA);
//                        break;
//                    case 48:
//                        city_lists = getResources().getStringArray(R.array.cities_WISCONSIN);
//                        break;
//                    case 49:
//                        city_lists = getResources().getStringArray(R.array.cities_WYOMING);
//                        break;
//                    case 50:
//                        city_lists = getResources().getStringArray(R.array.cities_DC);
//                        break;
//
//                    default:
//                        break;
//
//                }
//                autoComplete_destin();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//
//    public void autoComplete_destin()
//    {
//        autoCompleteTextView_Dest = (AutoCompleteTextView) findViewById(R.id.destinCityAutoComp);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, city_lists);
//        autoCompleteTextView_Dest.setAdapter(adapter);
//
//
//
//    }
//
//
//
//    private void checkEmpty()
//    {
//        String strDestin = txtDestinOriginCity.getText().toString();
//
//        if(strDestin.equals(""))
//        {
//            txtDestinOriginCity.setError("Please fill where you are going!");
//        }
//        else
//        {
//            params.add(new BasicNameValuePair("Destination", strDestin + ", " + state));
//            Intent intent = new Intent("coms309_29.tapp_5.Budget");
//            intent.putExtra("params", params);
//            startActivity(intent);
//        }
//    }
//}
