package com.example.submissiongithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub.Adapter.UserAdapter
import com.example.submissiongithub.Model.DetailUserModel
import com.example.submissiongithub.Model.Users
import com.example.submissiongithub.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    companion object {
        private val list = ArrayList<DetailUserModel>()
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnitemClikCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                with(intent){
                    putExtra(DetailActivity.USERNAME, data.login)
                }
                startActivity(intent)
            }

        })


        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.apply {
            rvUsers.layoutManager = layoutManager
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            btnSearch.setOnClickListener {
                searchUsers()

            }
            queryS.setOnKeyListener(View.OnKeyListener setOnKeyListener@{ v, keyCode, event ->
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                    searchUsers()
                    showLoading(true)
                    return@setOnKeyListener true
                }
                false
            })

        }

        userViewModel.getListUsers().observe(this) {
            if(it != null) {
                adapter.setList(it)

            }
            showLoading(false)
        }


    }

    private fun searchUsers() {
        binding.apply {
            val query = queryS.text.toString()
            if(query.isEmpty()) return
            userViewModel.findUsers(query)
            showLoading(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> {
                Intent(this, Activity_favorite::class.java).also{
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}