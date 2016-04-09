//package coms309_29.tapp_5;
//
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.jar.Attributes;
//
//import ch.boye.httpclientandroidlib.HttpResponse;
//import ch.boye.httpclientandroidlib.NameValuePair;
//import ch.boye.httpclientandroidlib.client.ClientProtocolException;
//import ch.boye.httpclientandroidlib.client.HttpClient;
//import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
//import ch.boye.httpclientandroidlib.client.methods.HttpPost;
//import ch.boye.httpclientandroidlib.impl.client.HttpClientBuilder;
//import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
//import ch.boye.httpclientandroidlib.util.EntityUtils;
//
//public class PointofInterest extends AppCompatActivity {
//
//    ArrayList<NameValuePair> params = null;
//    private RecyclerView recyclerView;
//    private RecAdapter adapter;
//    private List<Information> information;
//    private Button btnSelection;
//    private String responseString = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pointof_interest);
//        params = (ArrayList<NameValuePair>) getIntent().getSerializableExtra("params");
//
//
//
//        btnSelection = (Button) findViewById(R.id.button_POI);
//
//        information = new ArrayList<Information>();
//
//        information.add(new Information("Bar", "It is dummy description for Bar", false, R.drawable.ic_local_bar_black_36dp));
//        information.add(new Information("Food", "It is dummy description for Food", false, R.drawable.ic_restaurant_menu_black_36dp));
//        information.add(new Information("Historical Sites", "It is dummy description for Historical Sites", false, R.drawable.ic_nature_people_black_36dp));
//        information.add(new Information("Museum", "It is dummy description for Museum", false, R.drawable.ic_account_balance_black_36dp));
//        information.add(new Information("Theatre", "It is dummy description for theatre", false, R.drawable.ic_event_seat_black_36dp));
//        information.add(new Information("Leisure", "It is dummy description for Leisure", false, R.drawable.ic_directions_bike_black_36dp));
//
//
//
//
//        RecyclerView recList = (RecyclerView) findViewById(R.id.drawerList);
//        recList.setHasFixedSize(true);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recList.setLayoutManager(linearLayoutManager);
//
//        adapter = new RecAdapter(information);
//        recList.setAdapter(adapter);
//
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
////        btnSelection.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String data = "";
////                List<Information> infoList = ((RecAdapter)adapter).getInfoist();
////
////                for (int i =0; i < infoList.size(); i++)
////                {
////                    Information singleInfo = infoList.get(i);
////
////                    //**Comment by Gerald** add POIs to params array. Not sure how this will be implemented yet
////                    if(singleInfo.isSelected()==true)
////                    {
////                        data = data + "\n" + singleInfo.getTitle().toString();
////                    }
////                }
////
////                Toast.makeText(PointofInterest.this,"Selected Activty: " + data, Toast.LENGTH_SHORT).show();
////                startActivity(new Intent("coms309_29.tapp_5.Results"));
////
////            }
////        });
//
//
//    }
//
//    public void onClick(View v) {
//        String data = "";
//        String POIs = "";
//        List<Information> infoList = ((RecAdapter)adapter).getInfoist();
//
//        //Creates a String list of all the selected POIs
//        for (int i =0; i < infoList.size(); i++)
//        {
//
//            if(infoList.get(i).isSelected() == true)
//            {
//                POIs = POIs +  infoList.get(i).getTitle().toString() + ", ";
//            }
//        }
//
//        //Gets rid of the extra comma on the end of the POI list
//        if (POIs.endsWith(", ")) {
//            POIs = POIs.substring(0, POIs.length() - 2);
//        }
//
//
//        params.add(new BasicNameValuePair("POIs", POIs));
//        new MySQLConnect().execute();
//
//        int i = 0;
//        while (i < params.size()) {
//            Log.d("Param", params.get(i).toString());
//            i++;
//        }
//    }
//
//    class MySQLConnect extends AsyncTask<Void, Void, Void> {
//        protected Void doInBackground(Void... v) {
//            try {
//                HttpClient client = HttpClientBuilder.create().build();
//                HttpPost post = new HttpPost("http://proj-309-29.cs.iastate.edu/insertP.php");
//                post.setEntity(new UrlEncodedFormEntity(params));
//
//                HttpResponse response = client.execute(post);
//                responseString = EntityUtils.toString(response.getEntity());
//                Log.d("Response", responseString);
//
//            } catch(UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch(ClientProtocolException e) {
//                e.printStackTrace();
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//}
//
//
