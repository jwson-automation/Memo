package com.ssafy.jetpackall.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ssafy.jetpackall.database.NoteDatabase
import com.ssafy.jetpackall.database.NotesDto

private const val TAG = "NoteRepository_싸피"
private const val DATABASE_NAME = "note-database.db"
class NoteRepository private constructor(context: Context){

    //Database 생성과 동시에 초기화를 하고자 한다면 callback function에서 처리할 수 있다.
    private val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "onCreate: ")
        }
    }

    private val database : NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).addCallback(callback).build()

    private val noteDao = database.noteDao()

    fun getNotes() : LiveData<MutableList<NotesDto>> {
        return noteDao.getNotes()
    }
    fun getNote(id : Long) : LiveData<NotesDto> {
        return noteDao.getNote(id)
    }
    suspend fun insertNote(dto: NotesDto) = database.withTransaction {
        noteDao.insertNote(dto)
    }
    suspend fun updateNote(dto: NotesDto) = database.withTransaction {
        noteDao.updateNote(dto)
    }
    suspend fun deleteNote(dto : NotesDto) = database.withTransaction {
        noteDao.deleteNote(dto)
    }

    companion object{
        private var INSTANCE : NoteRepository? =null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }

        fun get() : NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}






