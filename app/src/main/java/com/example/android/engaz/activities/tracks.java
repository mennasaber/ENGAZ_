package com.example.android.engaz.activities;

import com.example.android.engaz.controllers.DB;
import com.example.android.engaz.models.track;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.engaz.R;
import com.example.android.engaz.adapters.GridAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class tracks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_tracks);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.track_dialog);

        final DB db = new DB(this);

        final ArrayList<track> tracks = db.selectTracks();

        GridView gridView = findViewById(R.id.gridView);
        final GridAdapter gridAdapter = new GridAdapter(this, tracks);
        gridView.setAdapter(gridAdapter);

        Button saveButton = dialog.findViewById(R.id.save_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        final EditText content = dialog.findViewById(R.id.addTrack_editText);

        FloatingActionButton button = findViewById(R.id.floatingActionButton2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.setText("");
                dialog.show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!content.getText().toString().trim().equals("")) {
                    track t = new track(content.getText().toString());
                    t.setId(db.insertTrack(t));
                    tracks.add(t);
                    gridAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Intent intent = new Intent(tracks.this, tasks.class);
                    intent.putExtra("tID", t.getId());
                    intent.putExtra("tContent", t.getContent());
                    startActivity(intent);
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(tracks.this, tasks.class);
                intent.putExtra("tID", tracks.get(i).getId());
                intent.putExtra("tContent", tracks.get(i).getContent());
                startActivity(intent);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                new AlertDialog.Builder(tracks.this)
                        .setTitle(Html.fromHtml("<font color='#4B343C'>Delete Track</font>"))
                        .setMessage(Html.fromHtml("<font color='#4B343C'>Are you sure you want to delete this track?</font>"))
                        .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteTrack(tracks.get(i));
                                tracks.remove(i);
                                gridAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show().getWindow().setBackgroundDrawableResource(R.color.color6);

                return true;
            }
        });

    }
}
