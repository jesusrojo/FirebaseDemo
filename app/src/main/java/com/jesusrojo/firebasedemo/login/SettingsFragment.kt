package com.jesusrojo.firebasedemo.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.jesusrojo.firebasedemo.R
import com.jesusrojo.firebasedemo.login.LoginViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
