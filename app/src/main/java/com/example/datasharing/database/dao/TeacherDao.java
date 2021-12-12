package com.example.datasharing.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datasharing.database.entities.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {

    @Query("SELECT * FROM teacher")
    List<Teacher> getAllTeachers();

    @Query("SELECT count(teacherId) FROM teacher")
    int getTeacherCount();

    @Query("SELECT * FROM teacher WHERE email = :email and password = :password")
    Teacher loginTeacher(String email, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTeacher(Teacher teacher);


    @Query("DELETE FROM teacher WHERE teacherId = :teacherId")
    int deleteTeacher(int teacherId);

//    @Query("SELECT count(appointmentId) FROM appointment where teacher_id =:teacherId and status = 0")
//    int totalPendingAppointment(int teacherId);
//
//    @Query("SELECT count(appointmentId) FROM appointment where teacher_id =:teacherId and status = 1")
//    int totalApprovedAppointment(int teacherId);

}