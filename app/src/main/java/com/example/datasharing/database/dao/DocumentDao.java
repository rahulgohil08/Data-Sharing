package com.example.datasharing.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datasharing.database.entities.Document;

import java.util.List;

@Dao
public interface DocumentDao {

    @Query("SELECT * FROM document")
    List<Document> getAllDocuments();

    @Query("SELECT count(documentId) FROM document")
    int getDocumentCount();

    @Query("SELECT * FROM document WHERE teacherId = :teacherId")
    List<Document> getTeacherDocuments(int teacherId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDocument(Document document);


    @Query("DELETE FROM document WHERE documentId = :documentId")
    void deleteDocument(int documentId);


}