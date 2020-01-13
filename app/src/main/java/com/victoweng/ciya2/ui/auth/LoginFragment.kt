package com.victoweng.ciya2.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import com.victoweng.ciya2.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {
    val TAG = LoginFragment::class.java.canonicalName
    private val RC_SIGN_IN = 1
    private lateinit var viewModel: LoginViewModel
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishOnBackPress()
    }

    private fun finishOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.d(TAG, "onBackPressed. Finishing Activity")
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "we are logged in")
            navController.navigate(R.id.action_loginFragment_to_searchHomeFragment)
        } else {
            Log.d(TAG, "not logged in")
        }

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onactivityResult " + requestCode)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(context, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            viewModel.handleAuthentication(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,
            LoginViewModelFactory(context!!, findNavController())
        ).get(LoginViewModel::class.java)
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context!!, googleSignInOptions)
    }
}
