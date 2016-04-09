package coms309_29.tapp_5;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by young on 10/24/2015.
 */
public class Results_pageradapter extends FragmentPagerAdapter {

    String[] results;
    String[] results_description;

    public Results_pageradapter(FragmentManager fm, Context context) {
        super(fm);

        Resources resources = context.getResources();

        results = resources.getStringArray(R.array.results_array);
        results_description = resources.getStringArray(R.array.resultsDescription_array);

    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(Results_Fragment.DescriptionKey, results_description[position]);

        Results_Fragment resultsFrag = new Results_Fragment();
        resultsFrag.setArguments(bundle);


        return resultsFrag;
    }
//
//    private int getDeviceImageID(int position) {
//
//        int id = 0;
//        switch(position)
//        {
//            case 0:
//                id = R.drawable.xxx;
//                break;
//            case 1:
//                id = R.drawable.yyy;
//                break;
//            case 2:
//                id = R.drawable.zzz;
//                break;
//
//        }
//
//        return id;
//
//    }

    @Override
    public int getCount() {
        return results.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return results[position];
    }
}
