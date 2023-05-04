package com.ssafy.jetpackall

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository
import kotlinx.coroutines.launch

// SavedStateViewModel
// 비정상 종료 되어도 handle은 유지된다.
class NoteViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var _id = handle.get<Long>("_id") ?: 0
        private set(value) {
            handle.set("-id", value)
            field = value
        }

    fun setId(id: Long) {
        _id = id
    }

    // 단건 조회, 입력, 수정
    val note: LiveData<NotesDto> = NoteRepository.get().getNote(_id)

    fun insert(dto: NotesDto) {
        viewModelScope.launch {
            NoteRepository.get().insertNote(dto)
        }
    }
    fun update(dto:NotesDto){
        viewModelScope.launch {
            NoteRepository.get().updateNote(dto)
        }
    }
}