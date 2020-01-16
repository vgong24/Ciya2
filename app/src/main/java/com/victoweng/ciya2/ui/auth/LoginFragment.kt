package com.victoweng.ciya2.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.victoweng.ciya2.R
import com.victoweng.ciya2.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    val TAG = LoginFragment::class.java.canonicalName
    private val RC_SIGN_IN = 1

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, providerFactory).get(LoginViewModel::class.java)
    }

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFinishOnBackPress()
    }

    private fun setupFinishOnBackPress() {
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

        viewModel.checkForAuthentication()

        viewModel.observeNavigationAction().observe(viewLifecycleOwner, Observer {
            findNavController().navigate(it)
        })

        viewModel.observeShouldShowSignInButton().observe(viewLifecycleOwner, Observer {
            setViewsVisibility(it)
        })

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun setViewsVisibility(enabled: Boolean) {
        sign_in_button.visibility = if (enabled) View.VISIBLE else View.GONE
        progress_bar_login.visibility = if (enabled) View.GONE else View.VISIBLE
    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_add_black_24dp)
                .setTheme(R.style.AppTheme)
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onactivityResult " + requestCode)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK || resultCode == Activity.RESULT_FIRST_USER) {
                Log.d(TAG, "check username");
                viewModel.userHasUserName()
            } else {
                signIn()
                Log.e(TAG, "Error logging in ${response?.toString()}. Retry Sign In")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context!!, googleSignInOptions)
    }
}
