package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.TeacherDao;
import com.example.datasharing.database.dao.UserDao;
import com.example.datasharing.databinding.ActivityTeacherDashboardBinding;
import com.example.datasharing.utils.SharedPrefManager;

public class TeacherDashboardActivity extends AppCompatActivity {

    private ActivityTeacherDashboardBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();

        binding = ActivityTeacherDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefManager = new SharedPrefManager(context);

        getSupportActionBar().setTitle("Teacher Dashboard");
        init();

    }


    private void init() {
        UserDao userDao = db.userDao();
        TeacherDao labDao = db.teacherDao();

//        new Thread(() -> {
//            int userCount = userDao.getUserCount();
//            int labCount = labDao.getLabCount();
//            runOnUiThread(() -> {
//                binding.tvTotalUsers.setText("" + userCount);
//                binding.tvTotalEmployee.setText("" + labCount);
//            });
//        }).start();

    }


    @Override
    protected void onStart() {
        super.onStart();

        boolean isLogin = sharedPrefManager.getBoolean("is_login");

        if (!isLogin) {
            openActivity(LoginActivity.class);
            finish();
        }
    }

    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }

    public void logout(View view) {
        sharedPrefManager.clear();
        openActivity(LoginActivity.class);
        finish();
    }


    public void openDocument(View view) {
        openActivity(TeacherDocumentActivity.class);
    }

    public void openAssignment(View view) {
        openActivity(TeacherAssignmentActivity.class);
    }
}