package com.ssafy.jetpackall.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes() : LiveData<MutableList<NotesDto>>

    @Query("SELECT * FROM note WHERE ID = (:id)")
    fun getNote(id: Long) : LiveData<NotesDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(dto : NotesDto)

    @Update
    suspend fun updateNote(dto : NotesDto)

    @Delete
    suspend fun deleteNote(dto : NotesDto)
}


