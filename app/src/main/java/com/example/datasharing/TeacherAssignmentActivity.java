package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.adapters.AssignmentAdapter;
import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.AssignmentDao;
import com.example.datasharing.database.entities.Assignment;
import com.example.datasharing.databinding.ActivityAssignmentBinding;
import com.example.datasharing.utils.FileHelperKt;
import com.example.datasharing.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class TeacherAssignmentActivity extends AppCompatActivity implements AssignmentAdapter.AssignmentInterface {

    private ActivityAssignmentBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    private AppDatabase db;
    private List<Assignment> assignmentList = new ArrayList<>();
    private AssignmentAdapter documentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();


        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Assignments");

    }


    private void init() {

        binding.fab.setOnClickListener(v -> openActivity(CreateAssignmentActivity.class));


        documentAdapter = new AssignmentAdapter(context, assignmentList, this, true);
        AssignmentDao dao = db.assignmentDao();

        binding.recyclerView.setAdapter(documentAdapter);
        int teacherId = sharedPrefManager.getInt("id");

        new Thread(() -> {
            assignmentList.clear();
            assignmentList.addAll(dao.getTeacherAssignments(teacherId));
            runOnUiThread(() -> {
                documentAdapter.notifyDataSetChanged();
            });
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }

    @Override
    public void onClick(Assignment assignment) {
        openPDFFile(assignment.getPath());
    }

    @Override
    public void onView(Assignment assignment) {

    }

    @Override
    public void onDelete(Assignment assignment) {
        int assignmentId = assignment.getAssignmentId();
        AssignmentDao dao = db.assignmentDao();
        new Thread(() -> {
            dao.deleteAssignment(assignmentId);
            runOnUiThread(() -> init());
        }).start();
    }

    @Override
    public void onLongClick(Assignment assignment) {

    }

    public void openPDFFile(String filePath) {
        Intent intent = FileHelperKt.getIntentForFile(filePath, context);
        startActivity(intent);
    }


}