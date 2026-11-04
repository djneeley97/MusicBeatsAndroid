package com.musicbeats.data

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

/** The signed-in user's profile images. null = not set yet, so the app shows the default picture. */
data class UserProfile(
    val avatarURL: String? = null,
    val headerURL: String? = null
)

/**
 * Uploads avatar/header images to Firebase Storage, saves their links on the
 * user's Firestore document (users/{uid}), and keeps `profile` up to date so
 * every screen showing it refreshes automatically.
 */
object ProfileService {

    private const val TAG = "ProfileService"
    private const val USERS = "users"

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    private var registration: ListenerRegistration? = null

    init {
        try {
            // Re-listen whenever someone signs in or out
            FirebaseAuth.getInstance().addAuthStateListener { auth ->
                registration?.remove()
                registration = null

                val uid = auth.currentUser?.uid
                if (uid == null) {
                    _profile.value = UserProfile()
                    return@addAuthStateListener
                }

                registration = FirebaseFirestore.getInstance()
                    .collection(USERS).document(uid)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            Log.e(TAG, "error loading profile: ${error.localizedMessage}")
                            return@addSnapshotListener
                        }
                        _profile.value = UserProfile(
                            avatarURL = snapshot?.getString("avatarURL"),
                            headerURL = snapshot?.getString("headerURL")
                        )
                    }
            }
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Firebase isn't set up yet: ${e.localizedMessage}")
        }
    }

    suspend fun uploadAvatar(uri: Uri) = upload(uri, "avatar", "avatarURL")

    suspend fun uploadHeader(uri: Uri) = upload(uri, "header", "headerURL")

    private suspend fun upload(uri: Uri, name: String, field: String): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("You need to be signed in to change your profile.")

        // New file name each time, so the app never shows an old cached copy
        val ref = FirebaseStorage.getInstance().reference
            .child("$USERS/$uid/${name}_${System.currentTimeMillis()}.jpg")

        ref.putFile(uri).await()
        val url = ref.downloadUrl.await().toString()

        FirebaseFirestore.getInstance()
            .collection(USERS).document(uid)
            .set(mapOf(field to url), SetOptions.merge())
            .await()

        return url
    }
}
