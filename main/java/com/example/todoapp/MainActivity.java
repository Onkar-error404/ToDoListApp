package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private Button addButton;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        addButton = findViewById(R.id.addButton);
        taskListView = findViewById(R.id.taskListView);

        taskDatabase = new TaskDatabase(this);
        ArrayList<Task> tasks = taskDatabase.getAllTasks();
        taskAdapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(taskAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskEditText.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    Task task = new Task(taskName);
                    taskDatabase.addTask(task);
                    taskAdapter.addTask(task);
                    taskEditText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskAdapter.getItem(position);
                taskDatabase.deleteTask(task);
                taskAdapter.removeTask(task);
                return true;
            }
        });

        taskAdapter.setOnTaskCompletedListener(new TaskAdapter.OnTaskCompletedListener() {
            @Override
            public void onTaskCompleted(Task task, boolean completed) {
                task.setCompleted(completed);
                taskDatabase.updateTask(task);
            }
        });
    }
}
