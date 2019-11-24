package com.example.android.engaz.adapters;
import com.example.android.engaz.R;
import com.example.android.engaz.models.track;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    View view;
    ArrayList<track> tracks;
    private LayoutInflater layoutInflater;


    public GridAdapter(Context context, ArrayList<track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public track getItem(int i) {
        return tracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.track, null);
        }
        track currentTrack = getItem(i);

        TextView content = view.findViewById(R.id.track_content);
        content.setText(currentTrack.getContent());

        return view;
    }
}