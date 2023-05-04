package com.ssafy.jetpackall

import android.app.Application
import com.ssafy.jetpackall.repository.NoteRepository

class NoteApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}