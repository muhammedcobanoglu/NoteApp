package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(note: Note): Long = noteRepository.addNote(note)
}