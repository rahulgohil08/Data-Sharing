package com.example.datasharing.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "document")
data class Document(
    @ColumnInfo(name = "teacherId")
    val teacherId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "path")
    val path: String,
) {
    @PrimaryKey(autoGenerate = true)
    var documentId: Int = 0
}