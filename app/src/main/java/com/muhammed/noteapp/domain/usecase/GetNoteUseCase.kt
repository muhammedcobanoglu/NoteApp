package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.domain.repository.NoteRepository
import com.muhammed.noteapp.data.util.Result
import kotlinx.coroutines.flow.Flow

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    fun execute(noteId: Int): Flow<Result<Note>> {
        return noteRepository.getNoteStream(noteId)
    }
}