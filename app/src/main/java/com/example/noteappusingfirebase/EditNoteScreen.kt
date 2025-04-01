package com.example.noteappusingfirebase

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditNoteScreen(viewModel: NoteViewModel, navController: NavController, noteId: String) {
    val notes by viewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId } ?: return

    var title by remember { mutableStateOf(note.title) }
    var description by remember { mutableStateOf(note.description) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Thay đổi ghi chú", style = MaterialTheme.typography.titleLarge, color = Color(0xFFFF9800)) // Cam đậm

            Spacer(modifier = Modifier.weight(1f))

            // Icon Button Delete (Thùng rác)
            IconButton(
                onClick = {
                    viewModel.deleteNote(note.id)
                    navController.popBackStack()
                }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Xoá", tint = Color(0xFFD32F2F)) // Đỏ
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2)) // Cam nhạt
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tiêu đề") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFFFF9800), // Cam đậm
                        unfocusedIndicatorColor = Color(0xFFFF9800).copy(alpha = 0.5f)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFFFF9800), // Cam đậm
                        unfocusedIndicatorColor = Color(0xFFFF9800).copy(alpha = 0.5f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút Save
        Button(
            onClick = {
                viewModel.updateNote(note.copy(title = title, description = description))
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)) // Cam đậm
        ) {
            Text("Lưu", color = Color.White)
        }
    }
}
