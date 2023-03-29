package com.example.submissiongithub.Model

import android.content.ClipData
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: ArrayList<Users>
)
