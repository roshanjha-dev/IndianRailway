package com.example.rohandsouza.indianrailways;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Dsouza on 24-Dec-17.
 */

public class CustomAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(DisplayAnnouncement object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        Display display;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)CustomAdapter.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.announcementlayout,parent,false);
            display = new Display();
            display.date = (TextView) row.findViewById(R.id.textView3);
            display.train_no = (TextView)row.findViewById(R.id.textView4);
            display.announcement = (TextView)row.findViewById(R.id.textView5);
            row.setTag(display);

        }
        else{
            display = (Display)row.getTag();
        }

        DisplayAnnouncement displayAnnouncement = (DisplayAnnouncement)CustomAdapter.this.getItem(position);
        display.date.setText(displayAnnouncement.getDate());
        display.train_no.setText(displayAnnouncement.getTrain_no());
        display.announcement.setText(displayAnnouncement.getInfo());
        return row;
    }

    static class Display
    {
        TextView date, train_no, announcement;

    }
}
