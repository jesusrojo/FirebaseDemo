package com.jesusrojo.firebasedemo.firebase.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jesusrojo.firebasedemo.R
import com.jesusrojo.firebasedemo.databinding.FirebaseActivityLayoutBinding
import com.jesusrojo.firebasedemo.firebase.ui.help.FirebaseHelp

class FirebaseActivity : AppCompatActivity() {

    private lateinit var firebaseHelp: FirebaseHelp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = FirebaseActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseHelp = FirebaseHelp(this, binding)
        firebaseHelp.create()
    }

    public override fun onStart() {
        super.onStart()
        firebaseHelp.start()
    }

    public override fun onResume() {
        super.onResume()
        firebaseHelp.resume()
    }

    public override fun onPause() {
        firebaseHelp.pause()
        super.onPause()
    }

    private fun signOutFirebase() {
        firebaseHelp.signOut()
    }

    // MENU TOP RIGHT
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOutFirebase()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}