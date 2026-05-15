package com.example.nammavastra.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammavastra.data.local.AppDatabase
import com.example.nammavastra.data.local.GalleryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).galleryDao()
    val allItems: Flow<List<GalleryItem>> = dao.getAllItems()

    fun addItem(item: GalleryItem) {
        viewModelScope.launch {
            dao.insertItem(item)
        }
    }

    fun updateItem(item: GalleryItem) {
        viewModelScope.launch {
            dao.updateItem(item)
        }
    }

    fun deleteItem(item: GalleryItem) {
        viewModelScope.launch {
            dao.deleteItem(item)
        }
    }
}
