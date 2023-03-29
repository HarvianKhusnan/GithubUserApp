package com.example.submissiongithub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub.Adapter.UserAdapter
import com.example.submissiongithub.ViewModel.FollowingViewModel
import com.example.submissiongithub.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var usrnm: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        usrnm = args?.getString(DetailActivity.USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUsersFollowing.setHasFixedSize(true)
            rvUsersFollowing.layoutManager = LinearLayoutManager(activity)
            rvUsersFollowing.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(usrnm)
        viewModel.getListFollowing().observe(viewLifecycleOwner){
            if(it!= null){
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isloading: Boolean) {
        if (isloading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }


}