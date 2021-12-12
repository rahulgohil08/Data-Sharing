package com.example.datasharing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.datasharing.database.AppDatabase;
import com.example.datasharing.database.dao.TeacherDao;
import com.example.datasharing.database.dao.UserDao;
import com.example.datasharing.database.entities.Teacher;
import com.example.datasharing.database.entities.User;
import com.example.datasharing.databinding.ActivityRegisterBinding;
import com.example.datasharing.utils.Config;
import com.example.datasharing.utils.SharedPrefManager;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;
    private String userType = "student";

    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "data-sharing").build();

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefManager = new SharedPrefManager(context);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        init();
    }


    private void init() {

        binding.radioGroup.setOnCheckedChangeListener((radioGroup, radioId) -> {

            if (radioId == R.id.radioStudent) {
                userType = "student";
            } else if (radioId == R.id.radioTeacher) {
                userType = "teacher";
            }

        });

        binding.btnLogin.setOnClickListener(view -> {
            String name = binding.edtName.getEditText().getText().toString();
            String email = binding.edtEmail.getEditText().getText().toString();
            String password = binding.edtPassword.getEditText().getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                Config.showToast(context, "all fields required");
                return;
            }
            doRegister(name, email, password);
        });


//        binding.tvSignUp.setOnClickListener(v -> openActivity(RegisterActivity.class));
    }

    private void doRegister(String name, String email, String password) {

        UserDao userDao = db.userDao();
        TeacherDao teacherDao = db.teacherDao();

        if (userType.equals("teacher")) {

            new Thread(() -> {
                Teacher teacher = new Teacher(name, email, password);
                long id = teacherDao.insertTeacher(teacher);
                runOnUiThread(() -> {
                    Config.showToast(context, "Registration successful");
                    finish();
                });
            }).start();


        } else if (userType.equals("student")) {

            new Thread(() -> {
                User user = new User(name, email, password);
                long id = userDao.insertUser(user);
                runOnUiThread(() -> {
                    Config.showToast(context, "Registration successful");
                    finish();
                });
            }).start();


        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        boolean isLogin = sharedPrefManager.getBoolean("is_login");

        if (isLogin) {
            String userType = sharedPrefManager.getString("role");
            if (userType.equals("student")) {
                openActivity(StudentDashboardActivity.class);
                finish();
            } else if (userType.equals("teacher")) {
                openActivity(TeacherDashboardActivity.class);
            }

        }
    }

    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }
}