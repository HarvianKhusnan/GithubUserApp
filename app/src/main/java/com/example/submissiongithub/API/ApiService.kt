package com.example.submissiongithub.API

import com.example.submissiongithub.Model.DetailUserModel
import com.example.submissiongithub.Model.UserResponse
import com.example.submissiongithub.Model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:  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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call <UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailUserModel>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getUserFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>
}