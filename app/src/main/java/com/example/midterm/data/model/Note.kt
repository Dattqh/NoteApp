package com.example.midterm.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID;

@Parcelize
data class Note(
    val id: String = UUID.randomUUID().toString(), // Luôn có ID ngẫu nhiên
    val title: String = "",
    val description: String = ""
) : Parcelable


