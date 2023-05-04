package com.ssafy.jetpackall

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository
import kotlinx.coroutines.launch

// 목록 조회, 단건 조회, 삭제
// 액티비티에서 할지, 뷰모델에서 할지는 취향의 영역
class NoteListViewModel : ViewModel() {
    val repo: NoteRepository = NoteRepository.get()

    // 목록 조회
    val noteList: LiveData<MutableList<NotesDto>> = repo.getNotes()

    // 단건 조회
    fun getNote(id: Long): LiveData<NotesDto> = repo.getNote(id)

    // 삭제
    fun deleteNote(dto: NotesDto) {
        viewModelScope.launch {
            repo.deleteNote(dto)
        }
    }


}