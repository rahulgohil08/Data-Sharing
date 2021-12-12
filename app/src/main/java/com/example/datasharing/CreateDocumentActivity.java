package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.util.FileUtil;

import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.DocumentDao;
import com.example.datasharing.database.entities.Document;
import com.example.datasharing.databinding.ActivityCreateDocumentBinding;
import com.example.datasharing.utils.Config;
import com.example.datasharing.utils.FileHelperKt;
import com.example.datasharing.utils.SharedPrefManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

public class CreateDocumentActivity extends AppCompatActivity {

    private static final String TAG = "DocumentCreate";
    private ActivityCreateDocumentBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    AppDatabase db;

    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        binding = ActivityCreateDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();


        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Create Document");
        init();

    }


    private void init() {

        binding.btnSubmit.setOnClickListener(v -> {

            String title = binding.edtTitle.getEditText().getText().toString();
            String description = binding.edtDescription.getEditText().getText().toString();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                Config.showToast(context, "all fields required");
                return;
            }
            if (TextUtils.isEmpty(filePath)) {
                Config.showToast(context, "please choose file");
                return;
            }

            createDocument(title, description);

        });
    }


    private void createDocument(String title, String description) {

        DocumentDao dao = db.documentDao();
        int teacherId = sharedPrefManager.getInt("id");

        new Thread(() -> {
            Document document = new Document(teacherId, title, description, filePath);
            dao.insertDocument(document);
            finish();
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