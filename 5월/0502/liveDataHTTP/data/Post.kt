package com.ssafy.jetpack.data

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
){
    override fun toString(): String {
        return "Post(id=$id, title='$title', userId=$userId)\n"
    }
}
