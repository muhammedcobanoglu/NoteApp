package com.muhammed.noteapp.data.source.local.datasourceImpl

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.source.local.NoteDao
import com.muhammed.noteapp.data.source.local.datasource.NoteLocalDataSource
import com.muhammed.noteapp.data.util.Result
import com.muhammed.noteapp.data.util.Result.Error
import com.muhammed.noteapp.data.util.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NoteLocalDataSourceImpl internal constructor(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteLocalDataSource {

    override fun getNotesStream(): Flow<Result<List<Note>>> {
        return noteDao.observeNotes().map {
            Success(it)
        }
    }

    override fun getNoteStream(noteId: Int): Flow<Result<Note>> {
        return noteDao.observeNoteById(noteId).map {
            Success(it)
        }
    }

    override suspend fun getNotes(): Result<List<Note>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(noteDao.getNotes())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getNote(noteId: String): Result<Note> = withContext(ioDispatcher) {
        try {
            val note = noteDao.getNoteById(noteId)
            if (note != null) {
                return@withContext Success(note)
            } else {
                return@withContext Error(Exception("Note not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun addNote(note: Note): Long = withContext(ioDispatcher) {
        noteDao.insertNote(note)
    }

    override suspend fun modifyNote(note: Note): Int = withContext(ioDispatcher) {
        noteDao.updateNote(note)
    }

    override suspend fun modifyNote(noteId: Int) {
        noteDao.updateNote(noteId, true)
    }

    override suspend fun deleteAllNotes() = withContext(ioDispatcher) {
        noteDao.deleteNotes()
    }

    override suspend fun deleteNote(noteId: Int): Int = withContext(ioDispatcher) {
        noteDao.deleteNoteById(noteId)
    }
}