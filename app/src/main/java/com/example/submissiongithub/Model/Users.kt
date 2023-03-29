package com.example.submissiongithub.Model

import com.google.gson.annotations.SerializedName


data class Users(
    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("avatar_url")
    val avatar_url: Int
)