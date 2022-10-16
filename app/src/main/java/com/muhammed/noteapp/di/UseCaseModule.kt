package com.muhammed.noteapp.di

import com.muhammed.noteapp.data.source.local.repository.NoteRepository
import com.muhammed.noteapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetAllNotesUseCaseUseCase(
        noteRepository: NoteRepository
    ): GetAllNotesUseCase {
        return GetAllNotesUseCase(noteRepository)
    }
    @Singleton
    @Provides
    fun provideGetNoteUseCaseUseCase(
        noteRepository: NoteRepository
    ): GetNoteUseCase {
        return GetNoteUseCase(noteRepository)
    }

    @Singleton
    @Provides
    fun provideAddNoteUseCase(
        noteRepository: NoteRepository
    ): AddNoteUseCase {
        return AddNoteUseCase(noteRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }

    @Singleton
    @Provides
    fun provideModifyNoteUseCase(
        noteRepository: NoteRepository
    ): ModifyNoteUseCase {
        return ModifyNoteUseCase(noteRepository)
    }
}