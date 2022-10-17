package com.muhammed.noteapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.muhammed.noteapp.R
import com.muhammed.noteapp.data.model.Note
import com.muhammed.noteapp.databinding.ItemNoteBinding
import com.muhammed.noteapp.util.GlideExtensions.loadWithUrl
import com.muhammed.noteapp.util.dateToString
import com.muhammed.noteapp.util.hide
import com.muhammed.noteapp.util.setRoundedBackground
import com.muhammed.noteapp.util.show

class NoteListAdapter(private val notes: MutableList<Note>) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){
    private var onTapListener: OnItemTapListener? = null

    inner class ViewHolder(private val itemBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(note: Note){
            itemBinding.titleTextView.text = note.title
            itemBinding.descriptionTextView.text = note.description
            itemBinding.imageView.loadWithUrl(note.url)
            val createdAt = note.createdAt?.dateToString()
            if (createdAt.isNullOrEmpty()) {
                itemBinding.createdAtTextView.hide()
            } else {
                itemBinding.createdAtTextView.show()
                itemBinding.createdAtTextView.text = createdAt
            }
            if (note.isModified) {
                itemBinding.isModified.setRoundedBackground(ContextCompat.getColor(itemBinding.isModified.context, R.color.purple_500), itemBinding.isModified.context, 0F, 0F, 8F, 0F)
            } else {
                itemBinding.isModified.hide()
            }
            itemBinding.root.setOnClickListener {
                onTapListener?.onTap(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    override fun getItemCount() = notes.size

    fun updateList(mNotes: List<Note>){
        notes.clear()
        notes.addAll(mNotes)
        notifyDataSetChanged()
    }

    interface OnItemTapListener {
        fun onTap(note: Note)
    }

    fun setItemTapListener(listener: OnItemTapListener){
        onTapListener = listener
    }
}