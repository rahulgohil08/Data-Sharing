package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class StudentAssignmentActivity extends AppCompatActivity implements AssignmentAdapter.AssignmentInterface {

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

        binding.fab.setVisibility(View.GONE);


        documentAdapter = new AssignmentAdapter(context, assignmentList, this,false);
        AssignmentDao dao = db.assignmentDao();

        binding.recyclerView.setAdapter(documentAdapter);

        new Thread(() -> {
            assignmentList.clear();
            assignmentList.addAll(dao.getAllAssignments());
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