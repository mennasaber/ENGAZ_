package com.example.android.engaz.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.engaz.R;
import com.example.android.engaz.activities.tasks;
import com.example.android.engaz.activities.tracks;
import com.example.android.engaz.controllers.DB;
import com.example.android.engaz.models.task;
import com.example.android.engaz.models.track;

import java.util.ArrayList;

public class GridAdapterTasks extends BaseAdapter {
    Context context;
    View view;
    ArrayList<task> tasks;
    private LayoutInflater layoutInflater;


    public GridAdapterTasks(Context context, ArrayList<task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public task getItem(int i) {
        return tasks.get(i);
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
            view = layoutInflater.inflate(R.layout.task, null);
        }
        final task currentTask = getItem(i);

        TextView content = view.findViewById(R.id.taskTitle);
        content.setText(currentTask.getContent());
        final CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTask.setStatue(checkBox.isChecked() ? 1 : 0);
                DB db = new DB(view.getContext());
                db.updateTask(currentTask);
            }
        });
        checkBox.setChecked(currentTask.isStatue() == 1);
        ImageButton imageButton = view.findViewById(R.id.deleteTask);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(context)
                        .setTitle(Html.fromHtml("<font color='#4B343C'>Delete Task</font>"))
                        .setMessage(Html.fromHtml("<font color='#4B343C'>Are you sure you want to delete this task?</font>"))
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DB db = new DB(view.getContext());
                                db.deleteTask(currentTask);
                                tasks.remove(currentTask);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show().getWindow().setBackgroundDrawableResource(R.color.color6);
            }
        });

        final ImageButton editButton = view.findViewById(R.id.editTask);

        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.track_dialog);

        Button saveButton = dialog.findViewById(R.id.save_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        final EditText content_ = dialog.findViewById(R.id.addTrack_editText);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                content_.setText(currentTask.getContent());
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
                if (!content_.getText().toString().trim().equals("")) {
                    currentTask.setContent(content_.getText().toString());
                    DB db = new DB(view.getContext());
                    db.updateTaskTitle(currentTask);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        return view;
    }
}
