package com.example.submissiongithub.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.API.ApiConfig
import com.example.submissiongithub.Model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<Users>>()

    fun setListFollowing(username: String){
        ApiConfig.getApiService()
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<Users>>{
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if(response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<Users>>{
        return listFollowing
    }
}