package com.victoweng.ciya2.ui.startactivity

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victoweng.ciya2.R
import com.victoweng.ciya2.constants.FireRepo
import com.victoweng.ciya2.data.UserProfile
import com.victoweng.ciya2.util.ToastUtil

class LoginViewModel(val context: Context, val navController: NavController) : ViewModel() {

   fun handleAuthentication(result : Task<AuthResult>) {
       if (result.isSuccessful) {
           Log.d("CLOWN", "is successful")
           userHasUserName()
       }else {
           Toast.makeText(context, "Sign in failed after auth " + result.exception?.message, Toast.LENGTH_SHORT).show()
       }
   }

    fun userHasUserName() {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.getReference("users")
            .child(FireRepo.getCurrentUserId()!!)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()){
                        val userProfile = dataSnapshot.getValue(UserProfile::class.java)
                        if (userProfile?.userName != null) {
                            navController.navigate(R.id.searchHomeFragment)
                        } else {
                            ToastUtil.show(context, "Create username...")
                            navController.navigate(R.id.action_loginFragment_to_createUsernameFragment)
                        }
                    } else {
                        Log.d("CLOWN", "doesnt exist...")
                        ToastUtil.show(context, "Create username...")
                        navController.navigate(R.id.action_loginFragment_to_createUsernameFragment)
                    }
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })
        Log.d("CLOWN", "check databaseRef")
    }
}

