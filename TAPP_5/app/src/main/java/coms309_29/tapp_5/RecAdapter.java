package coms309_29.tapp_5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by young on 10/16/2015.
 */
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.InfoViewHolder> {

    private List<Information> stInformation;
    public RecAdapter(List<Information> information)
    {
        this.stInformation = information;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row, viewGroup, false);

        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {

        final int pos = position;

        holder.vTitle.setText(stInformation.get(position).getTitle());
        holder.vDescription.setText(stInformation.get(position).getDescription());
        holder.vThumbnail.setImageResource(stInformation.get(position).getThumbnail());
        holder.chkSelected.setChecked(stInformation.get(position).isSelected());
        holder.chkSelected.setTag(stInformation.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CheckBox chb = (CheckBox) v;
                Information info = (Information) chb.getTag();

                info.setSelected(chb.isChecked());
                stInformation.get(pos).setSelected(chb.isChecked());

//                Toast.makeText(v.getContext(), "Clicked on checkbox: " + chb.getText() + " is " + chb.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });


//        Information info = stInformation.get(position);
//        holder.vTitle.setText(info.title);
//        holder.vDescription.setText(info.description);



    }


    @Override
    public int getItemCount() {
        return stInformation.size();
    }


    public static class InfoViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vDescription;
        protected ImageView vThumbnail;
        public CheckBox chkSelected;

        public InfoViewHolder(View itemView) {
            super(itemView);

            vTitle = (TextView) itemView.findViewById(R.id.interest_title);
            vDescription = (TextView) itemView.findViewById(R.id.interest_descript);
            vThumbnail = (ImageView) itemView.findViewById(R.id.icon_recyclerList);
            chkSelected = (CheckBox) itemView.findViewById(R.id.interest_checkBox);
        }
    }

    public List<Information> getInfoist()
    {
        return stInformation;
    }
}
