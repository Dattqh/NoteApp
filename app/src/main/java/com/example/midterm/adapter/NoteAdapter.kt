package com.example.midterm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midterm.R
import com.example.midterm.data.model.Note

class NoteAdapter(
    private val onItemClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    // ViewHolder class to hold the views for each item
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.desTextView)
        val deleteButton: ImageView = itemView.findViewById(R.id.closeButton)
        val noteItemLayout: View = itemView.findViewById(R.id.rowItemLayout)
    }

    // Inflate the item_note_ui.xml layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    // Bind the data to the views
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.description

        // Handle click on the entire item
        holder.noteItemLayout.setOnClickListener {
            onItemClick(note)
        }

        // Handle clicks on the edit and delete buttons
        holder.deleteButton.setOnClickListener { onDeleteClick(note) }
    }
}

// DiffUtil callback to efficiently update the list
class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}