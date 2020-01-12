package com.victoweng.ciya2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.adapter.FriendListAdapter
import com.victoweng.ciya2.ui.viewmodels.FriendsListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_friends_list.*

class FriendsListFragment : DaggerFragment() {

    val viewModel : FriendsListViewModel by lazy {
        ViewModelProviders.of(this).get(FriendsListViewModel::class.java)
    }

    val friendListAdapter : FriendListAdapter by lazy {
        FriendListAdapter{friendRequest -> viewModel.onRequestButtonClicked(friendRequest)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friend_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = friendListAdapter
        }

        viewModel.getFriendListLiveData().observe(viewLifecycleOwner, Observer {
            friendList -> friendListAdapter.updateFriendList(friendList)
        })

        viewModel.fetchFriendList()
    }
}
