package com.jesusrojo.firebasedemo.firebase.ui.help

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.jesusrojo.firebasedemo.firebase.model.FriendlyMessage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.jesusrojo.firebasedemo.BuildConfig
import com.jesusrojo.firebasedemo.databinding.FirebaseActivityLayoutBinding
import com.jesusrojo.firebasedemo.firebase.ui.adapter.FriendlyMessageAdapter
import com.jesusrojo.firebasedemo.firebase.ui.signin.SignInActivity


class FirebaseHelp(
    private val activity: AppCompatActivity,
    private val binding: FirebaseActivityLayoutBinding
) : AppCompatActivity() {


    private lateinit var llManager: LinearLayoutManager

    // Firebase instance variables
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var myAdapter: FriendlyMessageAdapter

    private val openDocument = activity.registerForActivityResult(MyOpenDocumentContract()) { uri ->
        onImageSelected(uri)
    }

    fun create() {
        // When running in debug mode, connect to the Firebase Emulator Suite
        // "10.0.2.2" is a special value which allows the Android emulator to
        // connect to "localhost" on the host computer. The port values are
        // defined in the firebase.json file.

//        if (BuildConfig.DEBUG) { //todo REAL
//            Firebase.database.useEmulator("10.0.2.2", 9000)
//            Firebase.auth.useEmulator("10.0.2.2", 9099)
//            Firebase.storage.useEmulator("10.0.2.2", 9199)
//        }

        // Initialize Firebase Auth and check if the user is signed in
        firebaseAuth = Firebase.auth
        if (isNotLoggedGoSignActivity()) return

        // Initialize Realtime Database
        firebaseDatabase = Firebase.database
        val messagesRef = firebaseDatabase.reference.child(MESSAGES_CHILD)

        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
        // See: https://github.com/firebase/FirebaseUI-Android
        val options = FirebaseRecyclerOptions.Builder<FriendlyMessage>()
            .setQuery(messagesRef, FriendlyMessage::class.java)
            .build()
        myAdapter = FriendlyMessageAdapter(options, getUserName())
        binding.progressBar.visibility = ProgressBar.INVISIBLE
        llManager = LinearLayoutManager(activity)
        llManager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = llManager
        binding.messageRecyclerView.adapter = myAdapter

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        myAdapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(binding.messageRecyclerView, myAdapter, llManager)
        )

        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        // When the send button is clicked, send a text message
        binding.sendButton.setOnClickListener {
            val friendlyMessage = FriendlyMessage(
                binding.messageEditText.text.toString(),
                getUserName(),
                getPhotoUrl(),
                null
            )
            firebaseDatabase.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
            binding.messageEditText.setText("")
        }

        // When the image button is clicked, launch the image picker
        binding.addMessageImageView.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }
    }

    fun start() = isNotLoggedGoSignActivity()

    fun pause() = myAdapter.stopListening()

    fun resume() = myAdapter.startListening()

    private fun onImageSelected(uri: Uri) {
        Log.d(TAG, "Uri: $uri")
        val user = firebaseAuth.currentUser
        val tempMessage = FriendlyMessage(null, getUserName(), getPhotoUrl(), LOADING_IMAGE_URL)
        firebaseDatabase.reference
                .child(MESSAGES_CHILD)
                .push()
                .setValue(
                        tempMessage,
                        DatabaseReference.CompletionListener { databaseError, databaseReference ->
                            if (databaseError != null) {
                                Log.w(TAG, "Unable to write message to database.", databaseError.toException())
                                return@CompletionListener
                            }

                            // Build a StorageReference and then upload the file
                            val key = databaseReference.key
                            val storageReference = Firebase.storage
                                    .getReference(user!!.uid)
                                    .child(key!!)
                                    .child(uri.lastPathSegment!!)
                            putImageInStorage(storageReference, uri, key)
                        })
    }

    private fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String?) {
        // First upload the image to Cloud Storage
        storageReference.putFile(uri)
            .addOnSuccessListener(
                activity
            ) { taskSnapshot -> // After the image loads, get a public downloadUrl for the image
                // and add it to the message.
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        val friendlyMessage =
                            FriendlyMessage(null, getUserName(), getPhotoUrl(), uri.toString())
                        firebaseDatabase.reference
                            .child(MESSAGES_CHILD)
                            .child(key!!)
                            .setValue(friendlyMessage)
                    }
            }
            .addOnFailureListener(activity) { e ->
                Log.w(TAG, "Image upload task was unsuccessful.", e)
            }
    }

    private fun isNotLoggedGoSignActivity(): Boolean {
        // Check if user is signed in
        if (firebaseAuth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            activity.startActivity(Intent(activity, SignInActivity::class.java))
            activity.finish()
            return true
        }
        return false
    }

    fun signOut() {
        AuthUI.getInstance().signOut(activity)
        activity.startActivity(Intent(activity, SignInActivity::class.java))
        activity.finish()
    }

    private fun getPhotoUrl(): String? {
        val user = firebaseAuth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    companion object {
        private const val TAG = "FirebaseHelp"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}
