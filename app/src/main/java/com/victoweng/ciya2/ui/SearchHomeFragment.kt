package com.victoweng.ciya2.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.victoweng.ciya2.R
import kotlinx.android.synthetic.main.fragment_search_home.*


class SearchHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check authentication
        val navController = findNavController()
        if (FirebaseAuth.getInstance().currentUser == null) {
            Log.d("CLOWN", "Switchinig to loginFragment cuz we not logged in")
            navController.navigate(R.id.loginFragment)
        }

        create_event.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_searchHomeFragment_to_eventCreationFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CLOWN", "SearchHomeFragment ondestroy")
    }


}
