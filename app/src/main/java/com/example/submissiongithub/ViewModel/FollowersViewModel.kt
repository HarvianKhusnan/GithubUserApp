package com.example.submissiongithub.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.API.ApiConfig
import com.example.submissiongithub.Model.UserResponse
import com.example.submissiongithub.Model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class FollowersViewModel: ViewModel() {
    val listFollower = MutableLiveData<ArrayList<Users>>()

    fun setListFollowers(username: String){
        ApiConfig.getApiService()
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if(response.isSuccessful){
                        listFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<Users>>{
        return listFollower
    }
}