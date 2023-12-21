package com.example.aplikasigithub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.databinding.FragmentFollowersBinding

open class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private var username: String? = null
    private var position:Int?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.isNestedScrollingEnabled = true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userFollowersModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserFollowersModel::class.java)
        userFollowersModel.followers.observe(this){
            setUserData(it)
        }
        userFollowersModel.following.observe(this){
            setUserData(it)
        }

        userFollowersModel.isLoading.observe(this){
            showLoading(it)
        }

        arguments?.let {
            username = it.getString(ARG_USERNAME)
            position = it.getInt(ARG_POSITION)
        }

        if (username != null){
            if (position == 1) {
                userFollowersModel.findFollowers(username!!, isFollowing = false)
            }else{
                userFollowersModel.findFollowing(username!!)
            }
        }

    }

    private fun setUserData(consumerReviews: List<UserItems?>?) {
        val adapter = UserAdapter()
        adapter.submitList(consumerReviews)
        binding.rvFollowers.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE

        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}