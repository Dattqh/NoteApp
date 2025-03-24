package com.example.midterm.ui


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midterm.data.model.Note
import com.example.midterm.data.repository.NoteRepository
import com.example.midterm.databinding.ActivityAddNoteUiBinding

class AddNoteUI : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteUiBinding
    private val noteRepository = NoteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val title = binding.addTitleEdt.text.toString()
            val description = binding.addDescriptionEdt.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val note = Note(title = title, description = description)
                noteRepository.addNote(
                    note,
                    onSuccess = {
                        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
