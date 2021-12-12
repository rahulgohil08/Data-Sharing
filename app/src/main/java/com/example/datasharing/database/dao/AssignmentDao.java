package com.example.datasharing.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datasharing.database.entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentDao {

    @Query("SELECT * FROM assignment")
    List<Assignment> getAllAssignments();

    @Query("SELECT count(assignmentId) FROM assignment")
    int getAssignmentCount();

    @Query("SELECT * FROM assignment WHERE teacherId = :teacherId")
    List<Assignment> getTeacherAssignments(int teacherId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAssignment(Assignment assignment);


    @Query("DELETE FROM assignment WHERE assignmentId = :assignmentId")
    void deleteAssignment(int assignmentId);


}