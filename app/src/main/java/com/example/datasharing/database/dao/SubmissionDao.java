package com.example.datasharing.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datasharing.database.entities.Submission;
import com.example.datasharing.database.entities.SubmissionWithStudent;

import java.util.List;

@Dao
public interface SubmissionDao {

    @Query("SELECT * FROM submission")
    List<Submission> getAllSubmissions();

    @Query("SELECT count(submissionId) FROM submission")
    int getSubmissionCount();

    @Query("SELECT * FROM submission WHERE assignmentId = :assignmentId")
    List<SubmissionWithStudent> getSubmissionByAssignmentId(int assignmentId);

    @Query("SELECT * FROM submission WHERE submissionId = :submissionId")
    SubmissionWithStudent getSubmissionById(int submissionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSubmission(Submission submission);

    @Query("DELETE FROM submission WHERE submissionId = :submissionId")
    void deleteSubmission(int submissionId);


}