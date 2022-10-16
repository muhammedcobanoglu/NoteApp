package com.muhammed.noteapp.domain.usecase

import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.source.local.repository.NoteRepository
import com.muhammed.noteapp.data.util.Result
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute() : Flow<Result<List<Note>>> {
        return noteRepository.getNotesStream()
    }
}