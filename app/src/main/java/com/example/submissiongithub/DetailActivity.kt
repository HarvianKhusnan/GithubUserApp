package com.example.submissiongithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissiongithub.Adapter.SectionsPagerAdapter
import com.example.submissiongithub.ViewModel.DetailViewModel
import com.example.submissiongithub.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    companion object{
        const val USERNAME = "extra_username"
        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_PHOTO = "extra_photo"

        private val TAB_TITLES = intArrayOf(
            R.string.follower_label,
            R.string.following_label
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        supportActionBar?.hide()
        setUser()




    }

    private fun setUser(){
        val usnm = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(EXTRA_USER_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_PHOTO)
        val bundle = Bundle()
        bundle.putString(USERNAME, usnm)

        detailBinding.apply {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, bundle)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        viewModel.setDetailUser(usnm.toString())
        viewModel.getUserDetail().observe(this){
            if (it !=null){
                detailBinding.apply {
                    tvFullname.text = it.name
                    tvUsersUsernames.text = it.login
                    followersTotal.text = it.followers.toString()
                    followingTotal.text = it.following.toString()
                    Glide.with(detailBinding.root)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(detailUserPic)
                }
            }


        }

        var _ischecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(usnm)
            withContext(Dispatchers.Main){
                if (count != null){
                    detailBinding.favButton.isChecked = true
                }else{
                    detailBinding.favButton.isChecked= false
                    _ischecked = false
                }

            }
        }

        detailBinding.favButton.setOnClickListener{
            _ischecked = !_ischecked
            if(_ischecked){
                if(usnm != null){
                    if(avatarUrl != null){
                        viewModel.addToFavorite(usnm, id, avatarUrl)
                    }
                }
            }else{
                viewModel.removeFromFavorite(id)
            }
            detailBinding.favButton.isChecked = _ischecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        detailBinding.apply {
            viewPager.adapter = sectionsPagerAdapter

        }
    }
}