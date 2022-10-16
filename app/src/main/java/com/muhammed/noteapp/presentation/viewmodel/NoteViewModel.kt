package com.muhammed.noteapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.data.util.Result
import com.muhammed.noteapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val modifyNoteUseCase: ModifyNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val state = MutableStateFlow<NoteState>(NoteState.Init)
    val mState: StateFlow<NoteState> get() = state

    private val notes = MutableStateFlow<List<Note>>(mutableListOf())
    val mNotes: StateFlow<List<Note>> get() = notes

    private val _note = MutableStateFlow<Note?>(null)
    val note : StateFlow<Note?> get() = _note

    init {
        getAllNotes()
    }

    private fun setLoading(){
        state.value = NoteState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = NoteState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = NoteState.ShowToast(message)
    }

    fun getAllNotes(){
        viewModelScope.launch {
            getAllNotesUseCase.execute()
                .onStart {
                    setLoading()
                }.catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }.collect { result ->
                    hideLoading()
                    when(result){
                        is Result.Success -> {
                            notes.value = result.data
                        }
                        is Result.Error -> {
                            showToast(result.exception.message?:"get all notes exception")
                        }
                    }
                }
        }
    }

    fun addNote(note: Note) = liveData(IO) {
        try {
            val noteId = addNoteUseCase.execute(note)
            emit(noteId)
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    fun modifyNote(note: Note) = liveData(IO) {
        try {
            val noteId = modifyNoteUseCase.execute(note)
            emit(noteId)
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    fun getNote(id: Int){
        viewModelScope.launch {
            getNoteUseCase.execute(id)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is Result.Success -> {
                            _note.value = result.data
                        }
                        is Result.Error -> {
                            showToast(result.exception.message?:"get note exception")
                        }
                    }
                }
        }
    }

    fun delete(id: Int) = liveData(IO) {
        try {
            val noteId = deleteNoteUseCase.execute(id)
            emit(noteId)
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}

sealed class NoteState {
    object Init : NoteState()
    data class IsLoading(val isLoading: Boolean) : NoteState()
    data class ShowToast(val message : String) : NoteState()
    object SuccessUpdate : NoteState()
    object SuccessDelete : NoteState()
}
