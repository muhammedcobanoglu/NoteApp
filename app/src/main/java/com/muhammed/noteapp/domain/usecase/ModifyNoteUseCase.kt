package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.source.local.repository.NoteRepository

class ModifyNoteUseCase(private val noteRepository: NoteRepository) {
    suspend fun execute(note: Note): Int {
        return noteRepository.modifyNote(note)
    }
}