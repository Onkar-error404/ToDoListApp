package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private OnTaskCompletedListener taskCompletedListener;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }
        TextView taskTextView = convertView.findViewById(R.id.taskTextView);
        CheckBox taskCheckBox = convertView.findViewById(R.id.taskCheckBox);

        taskTextView.setText(task.getName());
        taskCheckBox.setChecked(task.isCompleted());

        taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (taskCompletedListener != null) {
                    taskCompletedListener.onTaskCompleted(task, isChecked);
                }
            }
        });

        return convertView;
    }

    public void addTask(Task task) {
        add(task);
        notifyDataSetChanged();
    }

    public void removeTask(Task task) {
        remove(task);
        notifyDataSetChanged();
    }

    public void setOnTaskCompletedListener(OnTaskCompletedListener listener) {
        this.taskCompletedListener = listener;
    }

    public interface OnTaskCompletedListener {
        void onTaskCompleted(Task task, boolean completed);
    }
}
