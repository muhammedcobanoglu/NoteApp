package com.muhammed.noteapp.presentation.modify

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.muhammed.noteapp.R
import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.databinding.FragmentModifyNoteBinding
import com.muhammed.noteapp.presentation.viewmodel.NoteState
import com.muhammed.noteapp.presentation.viewmodel.NoteViewModel
import com.muhammed.noteapp.util.Constants
import com.muhammed.noteapp.util.NavigationExtensions.navigate
import com.muhammed.noteapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ModifyNoteFragment: Fragment(R.layout.fragment_modify_note) {

    private var _binding: FragmentModifyNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    private var noteId: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModifyNoteBinding.bind(view)
        getArgs()
        observe()
        update()
        delete()
        getNote()
    }

    private fun getArgs() {
        noteId = arguments?.getInt(Constants.NOTE_ID) ?: 0
    }

    private fun getNote(){
        if (noteId != 0){
            viewModel.getNote(noteId)
        }
    }

    private fun observe(){
        observeState()
        observeNote()
    }

    private fun update(){
        binding.modifyNoteButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()
            val imageUrl = binding.imageUrlEditText.text.toString().trim()
            if(validate(title, description)){
                viewModel.modifyNote(Note(noteId, title, description, imageUrl, true)).observe(viewLifecycleOwner) { resultId ->
                    if (resultId != 0) {
                        this@ModifyNoteFragment.navigate(R.id.action_modifyNoteFragment_to_noteListFragment)
                        Snackbar.make(
                            binding.root,
                            resources.getString(R.string.note_modified),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun delete(){
        binding.deleteNoteButton.setOnClickListener {
            viewModel.delete(noteId).observe(viewLifecycleOwner) { resultId ->
                if (resultId != -1) {
                    this@ModifyNoteFragment.navigate(R.id.action_modifyNoteFragment_to_noteListFragment)
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.note_deleted),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun observeState(){
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeNote(){
        viewModel.note.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { note ->
                note?.let { handleNote(it) }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: NoteState){
        when(state){
            is NoteState.SuccessUpdate -> this@ModifyNoteFragment.navigate(R.id.action_modifyNoteFragment_to_noteListFragment)
            is NoteState.SuccessDelete -> this@ModifyNoteFragment.navigate(R.id.action_modifyNoteFragment_to_noteListFragment)
            is NoteState.Init -> Unit
            is NoteState.ShowToast -> requireActivity().showToast(state.message)
            is NoteState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleLoading(isLoading: Boolean){
        binding.deleteNoteButton.isEnabled = !isLoading
        binding.modifyNoteButton.isEnabled = !isLoading
    }

    private fun handleNote(note: Note){
        binding.titleEditText.setText(note.title)
        binding.descriptionEditText.setText(note.description)
        binding.imageUrlEditText.setText(note.url)
    }

    private fun setTitleError(error: String?){
        binding.titleInput.error = error
    }

    private fun setDescriptionError(error: String?){
        binding.descriptionInput.error = error
    }

    private fun resetAllError(){
        setTitleError(null)
        setDescriptionError(null)
    }

    private fun validate(title: String, description: String) : Boolean {
        resetAllError()

        if(title.isEmpty()){
            setTitleError(getString(R.string.error_title))
            return false
        }

        if(description.isEmpty()){
            setDescriptionError(getString(R.string.error_description))
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}