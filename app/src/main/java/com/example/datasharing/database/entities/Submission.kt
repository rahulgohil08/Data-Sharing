package com.example.datasharing.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "submission")
data class Submission(
    @ColumnInfo(name = "assignmentId")
    val assignmentId: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "path")
    val path: String,
) {
    @PrimaryKey(autoGenerate = true)
    var submissionId: Int = 0
}