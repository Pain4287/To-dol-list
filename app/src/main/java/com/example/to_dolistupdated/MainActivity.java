package com.example.to_dolistupdated;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskEntity> taskList = new ArrayList<>();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        database = AppDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddTaskDialog());

        loadTasks();
    }

    private void showAddTaskDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task);

        EditText editTitle = dialog.findViewById(R.id.edit_task_title);
        EditText editDescription = dialog.findViewById(R.id.edit_task_description);
        Button btnSave = dialog.findViewById(R.id.btn_save_task);

        btnSave.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                TaskEntity task = new TaskEntity(0, title, description, System.currentTimeMillis(), 1, false); // Example categoryId 1
                new Thread(() -> {
                    database.taskDao().insertTask(task);
                    loadTasks();
                }).start();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadTasks() {
        new Thread(() -> {
            taskList.clear();
            taskList.addAll(database.taskDao().getAllTasks());
            runOnUiThread(() -> taskAdapter.notifyDataSetChanged());
        }).start();
    }
}
