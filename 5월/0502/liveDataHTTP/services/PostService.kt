package com.ssafy.jetpack.services


import com.ssafy.jetpack.data.Post
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    suspend fun getPosts(): List<Post>
                     //   :  Response<Posts>  //응답코드가 필요하다면...
}