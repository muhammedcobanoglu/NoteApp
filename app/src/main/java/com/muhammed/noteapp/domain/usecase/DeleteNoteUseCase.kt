package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(noteId: Int): Int = noteRepository.deleteNote(noteId)
}