package com.test.fakestore.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.test.fakestore.R
import com.test.fakestore.ui.login.LoginActivity
import com.test.fakestore.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var lottieLoading: LottieAnimationView
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameTextView = view.findViewById(R.id.nameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        phoneTextView = view.findViewById(R.id.phoneTextView)
        addressTextView = view.findViewById(R.id.addressTextView)
        lottieLoading = view.findViewById(R.id.lottieLoading)
        btnLogout = view.findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("MyAppPrefs", 0)
            prefs.edit().remove("USER_ID").apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        showLoading(true)

        val prefs: SharedPreferences =
            requireContext().getSharedPreferences("MyAppPrefs", 0)
        val userId = prefs.getInt("USER_ID", 1)

        profileViewModel.getUserProfile(userId).observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                nameTextView.text = "${it.name.firstname} ${it.name.lastname}"
                emailTextView.text = it.email
                phoneTextView.text = it.phone
                addressTextView.text = "${it.address.street}, ${it.address.city}, ${it.address.zipcode}"
                showLoading(false)
            }
        })

        return view
    }

    private fun showLoading(show: Boolean) {
        lottieLoading.visibility = if (show) View.VISIBLE else View.GONE
    }
}
