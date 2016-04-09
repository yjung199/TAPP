package coms309_29.tapp_5;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import coms309_29.tapp_5.tabs.SlidingTabLayout;

public class Results_Rex extends AppCompatActivity {
    private static List<Place> hotels = GetPlaces.search("hotels");
    private static List<Place> places = GetPlaces.search("places");
    private static int view = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results__rex);
        setTitle("Places");
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        ViewPager mPager;
        SlidingTabLayout mTabs;

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        mTabs.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (view == 0) {
            for (int i = 0; i < places.size(); i++) {
                GetPlaces.getAddress(new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude()), places.get(i).getName(), this);
                GetPlaces.t.run();
                try {
                    GetPlaces.t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < hotels.size(); i++) {
                GetPlaces.getAddress(new LatLng(hotels.get(i).getLatitude(), hotels.get(i).getLongitude()), hotels.get(i).getName(), this);
                GetPlaces.t.run();
                try {
                    GetPlaces.t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    public static class MyFragment extends Fragment {

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            ListView placesList = (ListView) layout.findViewById(R.id.hotelsList);
            ListView hotelList = (ListView) layout.findViewById(R.id.placesList);

            Bundle bundle = getArguments();
            if (bundle != null) {
                if (bundle.getInt("position") == 0) {
                    view = 0;
                    String[] names = new String[hotels.size()];

                    for (int i = 0; i < hotels.size(); i++) {
                        names[i] = hotels.get(i).getName();
                    }

                    hotelList.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_listview, names));
                } else {
                    view = 1;
                    String[] names = new String[places.size()];

                    for (int i = 0; i < places.size(); i++) {
                        names[i] = places.get(i).getName();
                    }
                    placesList.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_listview, names));
                }
            }

            hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent intent = new Intent("coms309_29.tapp_5.MapActivity");
                    intent.putExtra("lat", hotels.get(position).getLatitude());
                    intent.putExtra("long", hotels.get(position).getLongitude());
                    intent.putExtra("name", hotels.get(position).getName());
                    startActivity(intent);
                }
            });

            placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent intent = new Intent("coms309_29.tapp_5.MapActivity");
                    intent.putExtra("lat", places.get(position).getLatitude());
                    intent.putExtra("long", places.get(position).getLongitude());
                    intent.putExtra("name", places.get(position).getName());
                    startActivity(intent);
                }
            });

            return layout;
        }
    }

        class MyPagerAdapter extends FragmentPagerAdapter {

            String[] tabs;

            int icons[] = {R.drawable.ic_hotel_grey600_36dp, R.drawable.ic_compass_outline_grey600_36dp};
            String[] tabText = getResources().getStringArray(R.array.tabs);


            public MyPagerAdapter(FragmentManager fm) {
                super(fm);
                tabText = getResources().getStringArray(R.array.tabs);

            }

            @Override
            public Fragment getItem(int position) {
                return MyFragment.getInstance(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                Drawable drawable = getResources().getDrawable(icons[position]);
                drawable.setBounds(0, 0, 100, 100);
                ImageSpan imageSpan = new ImageSpan(drawable);
                SpannableString spannableString = new SpannableString(" ");
                spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                return spannableString;
            }

            @Override
            public int getCount() {
                return 2;
            }
        }
    }
