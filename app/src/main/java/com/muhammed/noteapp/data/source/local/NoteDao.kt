package com.muhammed.noteapp.data.source.local

import androidx.room.*
import com.muhammed.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun observeNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    fun observeNoteById(noteId: Int): Flow<Note>

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    suspend fun getNoteById(noteId: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note): Int

    @Query("UPDATE notes SET modified = :modified WHERE note_id = :noteId")
    suspend fun updateNote(noteId: Int, modified: Boolean): Int

    @Query("DELETE FROM notes WHERE note_id = :noteId")
    suspend fun deleteNoteById(noteId: Int): Int

    @Query("DELETE FROM notes")
    suspend fun deleteNotes()
}