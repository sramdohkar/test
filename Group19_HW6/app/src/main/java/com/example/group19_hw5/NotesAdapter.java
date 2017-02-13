package com.example.group19_hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shashank on 07/03/2016.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    List<Note> mData;
    Context mContext;
    int mResource;

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Note notes = mData.get(position);

        TextView text1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView text2 = (TextView) convertView.findViewById(R.id.textView2);

        text1.setText(notes.getNote());
        text2.setText(notes.getDate());

        return convertView;
    }
}