package com.musicbeats.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await


class AuthService {

    // null when google-services.json hasn't been added yet
    private val auth: FirebaseAuth? = try {
        FirebaseAuth.getInstance()
    } catch (e: IllegalStateException) {
        null
    }

    private val _currentUser = MutableStateFlow(auth?.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        auth?.addAuthStateListener { _currentUser.value = it.currentUser }
    }

    suspend fun signIn(email: String, password: String) {
        requireAuth().signInWithEmailAndPassword(email, password).await()
    }

    suspend fun createAccount(email: String, password: String) {
        requireAuth().createUserWithEmailAndPassword(email, password).await()
    }

    fun signOut() {
        requireAuth().signOut()
    }

    private fun requireAuth(): FirebaseAuth = auth ?: throw IllegalStateException(
        "Firebase isn't set up yet. Add google-services.json to the app folder."
    )
}
