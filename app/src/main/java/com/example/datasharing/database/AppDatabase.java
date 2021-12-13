package com.example.datasharing.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.datasharing.database.dao.AssignmentDao;
import com.example.datasharing.database.dao.DocumentDao;
import com.example.datasharing.database.dao.SubmissionDao;
import com.example.datasharing.database.dao.TeacherDao;
import com.example.datasharing.database.dao.UserDao;
import com.example.datasharing.database.entities.Assignment;
import com.example.datasharing.database.entities.Document;
import com.example.datasharing.database.entities.Submission;
import com.example.datasharing.database.entities.Teacher;
import com.example.datasharing.database.entities.User;

@Database(entities =
        {
                Teacher.class,
                User.class,
                Document.class,
                Assignment.class,
                Submission.class,
        },
        version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TeacherDao teacherDao();

    public abstract UserDao userDao();

    public abstract DocumentDao documentDao();

    public abstract AssignmentDao assignmentDao();

    public abstract SubmissionDao submissionDao();
}