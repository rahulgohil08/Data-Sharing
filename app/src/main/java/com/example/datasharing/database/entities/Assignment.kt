package com.example.datasharing.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment")
data class Assignment(
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
    var assignmentId: Int = 0
}