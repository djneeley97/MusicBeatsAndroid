package com.musicbeats.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTextField
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType
import com.musicbeats.ui.theme.hexColor
import kotlinx.coroutines.launch


@Composable
fun FirebaseLoginView(
    onLogin: suspend (email: String, password: String) -> Unit,
    onShowSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun performLogin() {
        scope.launch {
            try {
                errorText = null
                onLogin(email, password)
            } catch (e: Exception) {
                Log.e("FirebaseLoginView", "Error on login: ${e.localizedMessage}")
                errorText = e.localizedMessage
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(title = "Login", subtitle = "Use your existing account")

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

        // button
        PurpleButton(
            text = "Login",
            onClick = ::performLogin,
            modifier = Modifier
                .padding(top = 30.dp, bottom = 20.dp)
                .padding(horizontal = 80.dp)
                .fillMaxWidth()
        )

        errorText?.let { ErrorText(it) }

        HorizontalDivider(color = BeatsColors.Gray.copy(alpha = 0.4f))

        Column(
            modifier = Modifier.padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Don't have an account?", style = IosType.subheadline, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
            PurpleButton(text = "Sign up", onClick = onShowSignup, horizontalPadding = 66.dp)
        }
    }
}

// ---- Pieces shared by the login and signup screens ----

@Composable
internal fun AuthHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier.padding(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = BeatsColors.Gray, modifier = Modifier.size(60.dp))
        Text(title, style = IosType.largeTitle, color = hexColor("#ffffff"))
        Text(subtitle, style = IosType.subheadline, color = hexColor("#D7D7D7"))
    }
}

@Composable
internal fun LabeledField(label: String, field: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = IosType.subheadline, fontWeight = FontWeight.Medium, color = Color.White)
        field()
    }
}

@Composable
internal fun PurpleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: androidx.compose.ui.unit.Dp = 16.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(BeatsColors.Purple)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, style = IosType.body)
    }
}

@Composable
internal fun ErrorText(message: String) {
    Text(
        message,
        color = BeatsColors.Red,
        style = IosType.subheadline,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
private fun FirebaseLoginViewPreview() {
    FirebaseLoginView(onLogin = { _, _ -> }, onShowSignup = {})
}
