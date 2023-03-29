package com.example.submissiongithub.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submissiongithub.db.FavUserDao
import com.example.submissiongithub.db.FavoriteDatabase
import com.example.submissiongithub.db.UserFavorite

class FavViewModel(application: Application) : AndroidViewModel(application) {

    private var favUserDao: FavUserDao? = null
    private var favoriteDb: FavoriteDatabase? = null

    init{
        favoriteDb = FavoriteDatabase.getDatabase(application)
        favUserDao = favoriteDb?.userFavoriteDao()
    }

    fun getFavUser(): LiveData<List<UserFavorite>>? {
        return favUserDao?.getUserFavorite()
    }

}