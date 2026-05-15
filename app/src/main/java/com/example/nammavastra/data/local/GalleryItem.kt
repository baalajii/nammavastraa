package com.example.nammavastra.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery_items")
data class GalleryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String?,
    val title: String,
    val price: String,
    val phone: String,
    val timestamp: Long = System.currentTimeMillis()
)
