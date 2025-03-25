package com.example.midterm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midterm.data.model.Note
import com.example.midterm.data.repository.NoteRepository
import com.example.midterm.databinding.ActivityEditNoteUiBinding

class EditNoteUI : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteUiBinding
    private val noteRepository = NoteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        val note = intent.getParcelableExtra<Note>("Note") // Nhận Note từ Intent
        note?.let { originalNote ->
            binding.editTitleEdt.setText(originalNote.title)
            binding.editDescriptionEdt.setText(originalNote.description)

            binding.updateButton.setOnClickListener {
                val updatedTitle = binding.editTitleEdt.text.toString()
                val updatedDescription = binding.editDescriptionEdt.text.toString()

                if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                    // Giữ nguyên ID từ originalNote
                    val updatedNote = originalNote.copy(title = updatedTitle, description = updatedDescription)

                    noteRepository.updateNote(
                        updatedNote,
                        onSuccess = {
                            Toast.makeText(this, "Cap nhat ghi chu thanh cong!", Toast.LENGTH_SHORT).show()
                            finish() // Quay lại màn hình trước sau khi cập nhật thành công
                        },
                        onFailure = { exception ->
                            Toast.makeText(this, "Loi cap nhat: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(this, "Vui long dien day du thong tin!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
