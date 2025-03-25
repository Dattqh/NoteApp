package com.example.midterm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midterm.adapter.NoteAdapter
import com.example.midterm.data.repository.NoteRepository
import com.example.midterm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val noteRepository = NoteRepository()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteUI::class.java)
            startActivity(intent)
        }

        noteAdapter = NoteAdapter(
            onItemClick = { note ->
                val intent = Intent(this, EditNoteUI::class.java)
                intent.putExtra("Note", note)  // Truyền nguyên đối tượng Note, giữ nguyên ID
                startActivity(intent)
            },
            onDeleteClick = { note ->
                note.id?.let { id ->
                    noteRepository.deleteNote(
                        noteId = id,
                        onSuccess = { Toast.makeText(this, "Xoa ghi chu thanh cong", Toast.LENGTH_SHORT).show() },
                        onFailure = { exception -> Toast.makeText(this, "Loi xoa ghi chu: ${exception.message}", Toast.LENGTH_SHORT).show() }
                    )
                }
            }
        )

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = noteAdapter

        noteRepository.getNotes { notes ->
            noteAdapter.submitList(notes)
        }
    }
}