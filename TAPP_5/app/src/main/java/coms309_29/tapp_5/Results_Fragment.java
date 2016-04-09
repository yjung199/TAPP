package coms309_29.tapp_5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by young on 10/24/2015.
 */
public class Results_Fragment extends Fragment{

    public static final String DescriptionKey = "descriptionKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        Bundle bundle = getArguments();

        if (bundle != null)
        {
            String description = bundle.getString(DescriptionKey);

            setValues(view, description);
        }
        return view;
    }

    private void setValues(View view, String description) {

        TextView textView = (TextView) view.findViewById(R.id.fragment_result_txt);
        textView.setText(description);
    }

}
