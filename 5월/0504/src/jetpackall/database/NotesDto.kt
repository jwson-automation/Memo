package com.ssafy.jetpackall.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NotesDto(val TITLE:String= "title", val BODY:String="body") {

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

    constructor(id:Long, title:String, content:String): this(title, content){
        this.ID = id;
    }

    override fun toString(): String {
        return "NotesDto(TITLE='$TITLE', BODY='$BODY', ID=$ID)"
    }

}