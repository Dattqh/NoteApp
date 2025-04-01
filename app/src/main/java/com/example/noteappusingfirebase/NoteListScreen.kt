package com.example.noteappusingfirebase

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh Sách Ghi Chú", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF9800), // Màu cam đậm
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_note") },
                containerColor = Color(0xFFFF9800) // Màu cam đậm
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Thêm ghi chú", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White) // Nền trắng
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp)) // Bo góc 12dp
                            .clickable { navController.navigate("edit_note/${note.id}") },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2)), // Màu cam nhạt
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // Hiệu ứng bóng
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFFFF9800) // Màu chữ cam đậm
                            )
                            Spacer(modifier = Modifier.height(4.dp)) // Khoảng cách giữa tiêu đề và mô tả
                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
