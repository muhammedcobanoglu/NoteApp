package com.muhammed.noteapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammed.noteapp.data.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}