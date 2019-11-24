package com.example.android.engaz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.engaz.R;
import com.example.android.engaz.adapters.GridAdapter;
import com.example.android.engaz.adapters.GridAdapterTasks;
import com.example.android.engaz.controllers.DB;
import com.example.android.engaz.models.task;
import com.example.android.engaz.models.track;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class tasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        final int tID = getIntent().getIntExtra("tID", 0);
        final String tContent = getIntent().getStringExtra("tContent");
        TextView textView = findViewById(R.id.track_textView);
        textView.setText(tContent);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.track_dialog);

        final DB db = new DB(this);

        final ArrayList<task> tasks_array = db.selectTasks(tID);
        // Toast.makeText(this,tID + " ", Toast.LENGTH_LONG).show();
        GridView gridViewTask = findViewById(R.id.gridViewTasks);
        final GridAdapterTasks gridAdapterTasks = new GridAdapterTasks(this, tasks_array);
        gridViewTask.setAdapter(gridAdapterTasks);

        Button saveButton = dialog.findViewById(R.id.save_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        final EditText content = dialog.findViewById(R.id.addTrack_editText);

        FloatingActionButton button = findViewById(R.id.floatingActionButton1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.setText("");
                content.setHint("task");
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
                    task t = new task(tID, content.getText().toString(), 0);
                    t.setId(db.insertTask(t));
                    tasks_array.add(t);
                    gridAdapterTasks.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

}
