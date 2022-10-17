package com.muhammed.noteapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var noteId: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "modified") var isModified: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: Long? = null,
)