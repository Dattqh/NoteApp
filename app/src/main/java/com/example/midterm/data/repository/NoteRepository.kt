package com.example.midterm.data.repository

import com.example.midterm.data.model.Note
import com.google.firebase.database.*
import java.util.UUID

class NoteRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("notes")

    fun addNote(note: Note, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val noteId = note.id ?: UUID.randomUUID().toString()
        val newNote = Note(noteId, note.title, note.description)
        database.child(noteId).setValue(newNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        note.id?.let { id ->
            database.child(id).setValue(note)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } ?: run {
            onFailure(Exception("Note cannot be updated: ID is missing"))
        }
    }

    fun deleteNote(noteId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        database.child(noteId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getNotes(onDataChange: (List<Note>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = mutableListOf<Note>()
                for (data in snapshot.children) {
                    val note = data.getValue(Note::class.java)
                    note?.let { notes.add(it) }
                }
                onDataChange(notes)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}