package com.musicbeats.ui.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTextField
import com.musicbeats.ui.components.BeatsTopBar
import kotlinx.coroutines.launch

@Composable
fun FirebaseSignupView(
    onSignup: suspend (email: String, password: String) -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun performSignup() {
        scope.launch {
            try {
                errorText = null
                onSignup(email, password)
            } catch (e: Exception) {
                Log.e("FirebaseSignupView", "Error on signup: ${e.localizedMessage}")
                errorText = e.localizedMessage
            }
        }
    }

    Column(Modifier.fillMaxSize().imePadding()) {
        BeatsTopBar(title = "", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthHeader(title = "Sign Up", subtitle = "Create an account to continue")

            // form
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LabeledField("Email") {
                    BeatsTextField(email, { email = it }, "Your email", keyboardType = KeyboardType.Email)
                }
                LabeledField("Password") {
                    BeatsTextField(password, { password = it }, "Your password", isPassword = true)
                }
            }

            PurpleButton(
                text = "Sign up",
                onClick = ::performSignup,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth()
            )

            errorText?.let { ErrorText(it) }
        }
    }
}

@Preview
@Composable
private fun FirebaseSignupViewPreview() {
    FirebaseSignupView(onSignup = { _, _ -> }, onBack = {})
}
