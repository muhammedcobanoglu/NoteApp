package com.muhammed.noteapp.presentation.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammed.noteapp.R
import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.databinding.FragmentNoteListBinding
import com.muhammed.noteapp.presentation.viewmodel.NoteState
import com.muhammed.noteapp.presentation.viewmodel.NoteViewModel
import com.muhammed.noteapp.util.Constants
import com.muhammed.noteapp.util.NavigationExtensions.navigate
import com.muhammed.noteapp.util.hide
import com.muhammed.noteapp.util.show
import com.muhammed.noteapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list){
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteListBinding.bind(view)
        setupRecyclerView()
        observe()
        openNoteDetailPage()
        getNotes()
    }

    private fun openNoteDetailPage(){
        binding.createNewNote.setOnClickListener {
            this@NoteListFragment.navigate(R.id.addNoteFragment)
        }
    }

    private fun setupRecyclerView(){
        val mAdapter = NoteListAdapter(mutableListOf())
        binding.notesRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        mAdapter.setItemTapListener(object : NoteListAdapter.OnItemTapListener {
            override fun onTap(note: Note) {
                val bundle = bundleOf(Constants.NOTE_ID to note.id)
                this@NoteListFragment.navigate(R.id.action_noteListFragment_to_modifyNoteFragment, bundle)
            }
        })
    }

    private fun getNotes(){
        viewModel.getAllNotes()
    }

    private fun observeState(){
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle,  Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeNotes(){
        viewModel.mNotes
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { notes ->
                handleNotes(notes)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observe(){
        observeState()
        observeNotes()
    }

    private fun handleNotes(notes: List<Note>){
        binding.notesRecyclerView.adapter?.let {
            if(it is NoteListAdapter){
                it.updateList(notes)
            }
        }
    }

    private fun handleState(state: NoteState){
        when(state){
            is NoteState.IsLoading -> handleLoading(state.isLoading)
            is NoteState.ShowToast -> requireActivity().showToast(state.message)
            is NoteState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingProgressBar.show()
        }else{
            binding.loadingProgressBar.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}