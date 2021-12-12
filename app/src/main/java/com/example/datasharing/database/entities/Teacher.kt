package com.example.datasharing.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teacher")
data class Teacher(

    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String
) {
    @PrimaryKey(autoGenerate = true)
    var teacherId: Int = 0
}