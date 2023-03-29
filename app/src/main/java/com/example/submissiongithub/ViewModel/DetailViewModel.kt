package com.example.submissiongithub.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissiongithub.API.ApiConfig
import com.example.submissiongithub.Model.DetailUserModel
import com.example.submissiongithub.db.FavUserDao
import com.example.submissiongithub.db.FavoriteDatabase
import com.example.submissiongithub.db.UserFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<DetailUserModel>()

    private var favoriteuserDao: FavUserDao? = null
    private var favoriteDb: FavoriteDatabase? = null

    init {
        favoriteDb = FavoriteDatabase.getDatabase(application)
        favoriteuserDao = favoriteDb?.userFavoriteDao()

    }


    fun setDetailUser(username: String){
        ApiConfig.getApiService()
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserModel>{
                override fun onResponse(
                    call: Call<DetailUserModel>,
                    response: Response<DetailUserModel>
                ) {
                    if(response.isSuccessful){
                        detailUser.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<DetailUserModel>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())

                }

            })

    }



    fun getUserDetail(): LiveData<DetailUserModel> {
        return detailUser
    }

    fun addToFavorite(username: String, id: Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(
                username,
                id,
                avatarUrl
            )
            favoriteuserDao?.addToUserFavorite(user)
        }
    }

    suspend fun checkUser(id: String?) = favoriteuserDao?.checkUserFavorite(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch{
            favoriteuserDao?.deleteUserFavorite(id)

        }
    }



}