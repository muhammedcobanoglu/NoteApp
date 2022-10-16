package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.data.source.local.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(noteId: Int): Int = noteRepository.deleteNote(noteId)
}