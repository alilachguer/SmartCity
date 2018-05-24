package com.example.ali.smartcity.data;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ali.smartcity.R;

import java.util.ArrayList;

public class GroupeAdapter extends ArrayAdapter<Groupe> {

    private Activity activity;
    private ArrayList<Groupe> groupes;
    private static LayoutInflater inflater = null;

    public GroupeAdapter (Activity activity, int textViewResourceId, ArrayList<Groupe> groupes) {
        super(activity, textViewResourceId, groupes);
        try {
            this.activity = activity;
            this.groupes = groupes;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return groupes.size();
    }

    public Posts getItem(Posts position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView groupeId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.news_item, null);
                holder = new ViewHolder();
                holder.display_name = (TextView) vi.findViewById(R.id.news_item_title);
                holder.groupeId = (TextView) vi.findViewById(R.id.news_item_date);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_name.setText(groupes.get(position).nom);
            holder.groupeId.setText(groupes.get(position).id);


        } catch (Exception e) {


        }
        return vi;
    }


}
