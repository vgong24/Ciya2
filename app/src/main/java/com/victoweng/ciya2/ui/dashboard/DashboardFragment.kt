package com.victoweng.ciya2.ui.dashboard


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

import com.victoweng.ciya2.R
import com.victoweng.ciya2.repository.database.AppDatabase
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DashboardFragment : DaggerFragment() {

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_out.setOnClickListener {
            AuthUI.getInstance().signOut(context!!)
                .addOnCompleteListener {
                    Log.d("CLOWN","Signed out via AuthUI")
                    findNavController().navigate(R.id.action_dashboardFragment_to_searchHomeFragment)
                }
        }

        button_clear_database.setOnClickListener {
            CoroutineScope(IO).launch {
                val dao = appDatabase.eventsDao()
                val list = dao.getAllEvents()
                Log.d("CLOWN", "current size: ${list?.size}")
                dao.deleteAllEvents()
                val list2 = dao.getAllEvents()
                Log.d("CLOWN", "current size: ${list2?.size}")
            }
        }
    }
}
