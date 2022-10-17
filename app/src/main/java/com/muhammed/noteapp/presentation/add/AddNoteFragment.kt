package com.muhammed.noteapp.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.muhammed.noteapp.R
import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.databinding.FragmentAddNoteBinding
import com.muhammed.noteapp.presentation.viewmodel.NoteViewModel
import com.muhammed.noteapp.util.NavigationExtensions.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment: Fragment(R.layout.fragment_add_note) {
    private var _binding : FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNoteBinding.bind(view)
        addNote()
    }

    private fun addNote(){
        binding.addNoteButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()
            val imageUrl = binding.imageUrlEditText.text.toString().trim()
            if(validate(title, description)){
                viewModel.addNote(Note(0, title, description, imageUrl, false, System.currentTimeMillis())).observe(viewLifecycleOwner) { noteId ->
                    if (noteId != 0L) {
                        this@AddNoteFragment.navigate(R.id.action_addNoteFragment_to_noteListFragment)
                        Snackbar.make(
                            binding.root,
                            resources.getString(R.string.new_note_added),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}