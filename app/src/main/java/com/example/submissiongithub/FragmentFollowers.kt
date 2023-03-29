package com.example.submissiongithub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub.Adapter.UserAdapter
import com.example.submissiongithub.ViewModel.DetailViewModel
import com.example.submissiongithub.ViewModel.FollowersViewModel
import com.example.submissiongithub.ViewModel.FollowingViewModel
import com.example.submissiongithub.databinding.FragmentFollowersBinding


class FragmentFollowers : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var usrnm: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argt = arguments
        if (argt != null) {
            usrnm = argt.getString(DetailActivity.USERNAME).toString()
            _binding = FragmentFollowersBinding.bind(view)

            adapter = UserAdapter()
            adapter.notifyDataSetChanged()



            binding.apply {
                rvUsersFollower.setHasFixedSize(true)
                rvUsersFollower.layoutManager = LinearLayoutManager(activity)
                rvUsersFollower.adapter = adapter

            }
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
            viewModel.setListFollowers(usrnm)
            viewModel.getListFollowers().observe(viewLifecycleOwner) {
                if (it != null){
                    adapter.setList(it)
                }
            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}