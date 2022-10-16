package com.muhammed.noteapp.di

import android.content.Context
import androidx.room.Room
import com.muhammed.noteapp.data.source.local.NoteDatabase
import com.muhammed.noteapp.data.source.local.repository.NoteRepository
import com.muhammed.noteapp.data.source.local.repository.NoteRepositoryImpl
import com.muhammed.noteapp.data.source.local.repository.datasource.NoteLocalDataSource
import com.muhammed.noteapp.data.source.local.repository.datasourceImpl.NoteLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalNotesDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNotesRepository(
        @LocalNotesDataSource noteLocalDataSource: NoteLocalDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NoteRepository {
        return NoteRepositoryImpl(noteLocalDataSource, ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @LocalNotesDataSource
    @Provides
    fun provideNotesLocalDataSource(
        database: NoteDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NoteLocalDataSource {
        return NoteLocalDataSourceImpl(database.noteDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "Notes.db"
        ).build()
    }
}