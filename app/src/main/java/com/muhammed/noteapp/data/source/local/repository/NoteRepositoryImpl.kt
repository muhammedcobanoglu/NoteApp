package com.muhammed.noteapp.data.source.local.repository

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.source.local.repository.datasource.NoteLocalDataSource
import com.muhammed.noteapp.data.util.Result
import com.muhammed.noteapp.data.util.Result.Success
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val notesLocalDataSource: NoteLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {

    override suspend fun getNotes(): Result<List<Note>> {
        return notesLocalDataSource.getNotes()
    }

    override fun getNotesStream(): Flow<Result<List<Note>>> {
        return notesLocalDataSource.getNotesStream()
    }

    override fun getNoteStream(noteId: Int): Flow<Result<Note>> {
        return notesLocalDataSource.getNoteStream(noteId)
    }

    override suspend fun getNote(noteId: String): Result<Note> {
        return notesLocalDataSource.getNote(noteId)
    }

    override suspend fun addNote(note: Note): Long {
        var noteId = 0L
        coroutineScope {
            launch {
                noteId = notesLocalDataSource.addNote(note) }
        }
        return noteId
    }

    override suspend fun modifyNote(note: Note): Int {
        var noteId = -1
        coroutineScope {
            launch {
                noteId = notesLocalDataSource.modifyNote(note)
            }
        }
        return noteId
    }

    override suspend fun modifyNote(noteId: String) {
        withContext(ioDispatcher) {
            (getNoteWithId(noteId) as? Success)?.let { it ->
                modifyNote(it.data)
            }
        }
    }

    override suspend fun deleteAllNotes() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { notesLocalDataSource.deleteAllNotes() }
            }
        }
    }

    override suspend fun deleteNote(id: Int): Int {
        var noteId = -1
        coroutineScope {
            launch {
                noteId = notesLocalDataSource.deleteNote(id)
            }
        }
        return noteId
    }

    private suspend fun getNoteWithId(id: String): Result<Note> {
        return notesLocalDataSource.getNote(id)
    }
}