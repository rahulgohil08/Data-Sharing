package com.example.datasharing.database.entities

import androidx.room.Embedded
import androidx.room.Relation


data class SubmissionWithStudent(

    @Embedded  val submission: Submission,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val user: User,
)