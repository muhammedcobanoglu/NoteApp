package com.muhammed.noteapp.data.source.local.datasource

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.util.Result
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun getNotesStream(): Flow<Result<List<Note>>>

    suspend fun getNotes(): Result<List<Note>>

    fun getNoteStream(noteId: Int): Flow<Result<Note>>

    suspend fun getNote(noteId: String): Result<Note>

    suspend fun addNote(note: Note): Long

    suspend fun modifyNote(note: Note): Int

    suspend fun modifyNote(noteId: Int)

    suspend fun deleteAllNotes()

    suspend fun deleteNote(noteId: Int): Int
}