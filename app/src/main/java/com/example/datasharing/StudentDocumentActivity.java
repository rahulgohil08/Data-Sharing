package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.adapters.DocumentAdapter;
import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.DocumentDao;
import com.example.datasharing.database.entities.Document;
import com.example.datasharing.databinding.ActivityDocumentBinding;
import com.example.datasharing.utils.FileHelperKt;
import com.example.datasharing.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class StudentDocumentActivity extends AppCompatActivity implements DocumentAdapter.DocumentInterface {

    private ActivityDocumentBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    private AppDatabase db;
    private List<Document> documentList = new ArrayList<>();
    private DocumentAdapter documentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();


        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Documents");

    }


    private void init() {

        binding.fab.setVisibility(View.GONE);

        documentAdapter = new DocumentAdapter(context, documentList, this);
        DocumentDao dao = db.documentDao();

        binding.recyclerView.setAdapter(documentAdapter);

        new Thread(() -> {
            documentList.clear();
            documentList.addAll(dao.getAllDocuments());
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
    public void onClick(Document document) {
        openPDFFile(document.getPath());
    }

    public void openPDFFile(String filePath) {
        Intent intent = FileHelperKt.getIntentForFile(filePath, context);
        startActivity(intent);
    }

    @Override
    public void onDelete(Document document) {
        int documentId = document.getDocumentId();
        DocumentDao dao = db.documentDao();
        new Thread(() -> {
            dao.deleteDocument(documentId);
            runOnUiThread(() -> init());
        }).start();
    }
}