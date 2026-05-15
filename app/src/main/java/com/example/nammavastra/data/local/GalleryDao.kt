package com.example.nammavastra.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {
    @Query("SELECT * FROM gallery_items ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<GalleryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: GalleryItem)

    @Update
    suspend fun updateItem(item: GalleryItem)

    @Delete
    suspend fun deleteItem(item: GalleryItem)
}
