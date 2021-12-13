package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.adapters.SubmissionAdapter;
import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.SubmissionDao;
import com.example.datasharing.database.entities.SubmissionWithStudent;
import com.example.datasharing.databinding.ActivitySubmissionBinding;
import com.example.datasharing.utils.FileHelperKt;
import com.example.datasharing.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class TeacherSubmissionActivity extends AppCompatActivity implements SubmissionAdapter.SubmissionInterface {

    private ActivitySubmissionBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    private AppDatabase db;
    private List<SubmissionWithStudent> documentList = new ArrayList<>();
    private SubmissionAdapter submissionAdapter;
    private int assignmentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubmissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        assignmentId = intent.getIntExtra("assignment_id", 0);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();


        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Submissions");

    }


    private void init() {


        submissionAdapter = new SubmissionAdapter(context, documentList, this, true);
        SubmissionDao dao = db.submissionDao();

        binding.recyclerView.setAdapter(submissionAdapter);
        int teacherId = sharedPrefManager.getInt("id");

        new Thread(() -> {
            documentList.clear();
            documentList.addAll(dao.getSubmissionByAssignmentId(assignmentId));
            runOnUiThread(() -> {
                submissionAdapter.notifyDataSetChanged();
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


    public void openPDFFile(String filePath) {
        Intent intent = FileHelperKt.getIntentForFile(filePath, context);
        startActivity(intent);
    }


    @Override
    public void onClick(SubmissionWithStudent submissionWithStudent) {
        openPDFFile(submissionWithStudent.getSubmission().getPath());
    }

    @Override
    public void onView(SubmissionWithStudent submissionWithStudent) {

    }
}