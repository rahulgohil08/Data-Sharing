package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.AssignmentDao;
import com.example.datasharing.database.dao.SubmissionDao;
import com.example.datasharing.database.entities.Assignment;
import com.example.datasharing.database.entities.Submission;
import com.example.datasharing.databinding.ActivityCreateAssignmentBinding;
import com.example.datasharing.databinding.ActivityCreateSubmissionBinding;
import com.example.datasharing.utils.Config;
import com.example.datasharing.utils.FileHelperKt;
import com.example.datasharing.utils.SharedPrefManager;

import java.io.File;

public class CreateSubmissionActivity extends AppCompatActivity {

    private static final String TAG = "CreateSubmission";
    private ActivityCreateSubmissionBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    AppDatabase db;

    private String filePath = "";
    private int assignmentId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        binding = ActivityCreateSubmissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        assignmentId = intent.getIntExtra("assignment_id", 0);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();


        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Submission");
        init();
        fetchAssignment();

    }


    private void init() {

        binding.btnSubmit.setOnClickListener(v -> {

//            String title = binding.edtTitle.getEditText().getText().toString();
//            String description = binding.edtDescription.getEditText().getText().toString();
//
//            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
//                Config.showToast(context, "all fields required");
//                return;
//            }


            if (TextUtils.isEmpty(filePath)) {
                Config.showToast(context, "please choose file");
                return;
            }

            createSubmission();

        });


    }


    private void fetchAssignment() {
        AssignmentDao dao = db.assignmentDao();

        new Thread(() -> {
            Assignment data = dao.getAssignment(assignmentId);

            runOnUiThread(() -> {
                binding.edtTitle.getEditText().setText(data.getTitle());
            });

        }).start();
    }


    private void createSubmission() {

        SubmissionDao dao = db.submissionDao();
        int id = sharedPrefManager.getInt("id");

        new Thread(() -> {
            Submission data = new Submission(assignmentId, id, filePath);
            dao.insertSubmission(data);
            runOnUiThread(()->{
                Config.showToast(context, "Assignment submitted");
                finish();
            });
        }).start();
    }


    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                Log.e(TAG, "File Uri: " + uri.toString());

                File file = FileHelperKt.getFile(context, uri, ".pdf");
                Log.e(TAG, "File PATH: " + file);

                filePath = file.getAbsolutePath();
//                openPDFFile(file);


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void openPDFFile(File filePath) {
        Intent intent = FileHelperKt.getIntentForFile(filePath.getAbsolutePath(), context);
        startActivity(intent);
    }


}