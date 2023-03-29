package com.example.submissiongithub.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert
    fun addToUserFavorite(userFavorite: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getUserFavorite(): LiveData<List<UserFavorite>>

    @Query( "SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun checkUserFavorite(id: String?): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun deleteUserFavorite(id: Int): Int


}