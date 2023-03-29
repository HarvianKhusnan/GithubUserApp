package com.example.submissiongithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub.Adapter.UserAdapter
import com.example.submissiongithub.Model.Users
import com.example.submissiongithub.ViewModel.FavViewModel
import com.example.submissiongithub.databinding.ActivityFavoriteBinding
import com.example.submissiongithub.db.UserFavorite

class Activity_favorite : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var faViewModel: FavViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        faViewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        adapter.setOnitemClikCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@Activity_favorite, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_USER_ID, data.url)
                    it.putExtra(DetailActivity.EXTRA_PHOTO, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@Activity_favorite)
            rvFav.adapter = adapter
        }

        faViewModel.getFavUser()?.observe(this, {
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })

    }

    private fun mapList(users: List<UserFavorite>):ArrayList<Users> {
        val listUsers = ArrayList<Users>()
        for (user in users){
            val userMapped = Users(
                user.username,
                user.avatar,
                user.id
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }


}