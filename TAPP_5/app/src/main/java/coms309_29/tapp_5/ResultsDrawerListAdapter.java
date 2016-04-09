package coms309_29.tapp_5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by young on 11/2/2015.
 */
public class ResultsDrawerListAdapter extends RecyclerView.Adapter<ResultsDrawerListAdapter.mViewHolder> {

    List<ResultsDrawerListInformation> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public ResultsDrawerListAdapter(Context context, List<ResultsDrawerListInformation> data)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.results_drawerlist_custom_row, parent, false);
//        Log.d("YOUNG", "onCreateHolder called");
        mViewHolder holder = new mViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {

        ResultsDrawerListInformation current = data.get(position);
//        Log.d("YOUNG", "onBindViewHolder called "+position);
        holder.icon.setImageResource(current.iconID);
        holder.title.setText(current.title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Clicked at " + position, Toast.LENGTH_SHORT).show();
                if(position == 0)
                {
                    Intent intent = new Intent("coms309_29.tapp_5.MainActivity");
                    v.getContext().startActivity(intent);
                }
                else if (position == 1)
                {
                    Intent intent = new Intent("coms309_29.tapp_5.Destination");
                    v.getContext().startActivity(intent);
                }
                else if (position == 2)
                {
                    Intent intent = new Intent("coms309_29.tapp_5.Budget");
                    v.getContext().startActivity(intent);
                }
                else if (position == 3)
                {
                    Intent intent = new Intent("coms309_29.tapp_5.Date");
                    v.getContext().startActivity(intent);
                }
                else if (position == 4)
                {
                    Intent intent = new Intent("coms309_29.tapp_5.PointofInterest");
                    v.getContext().startActivity(intent);
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView icon;


        public mViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listTextDrawerList);
            icon = (ImageView) itemView.findViewById(R.id.listIconDrawerList);


        }
    }
}
