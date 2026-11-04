package com.musicbeats.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType


@Composable
fun BeatsTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    applyStatusBarPadding: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .then(if (applyStatusBarPadding) Modifier.statusBarsPadding() else Modifier)
            .height(52.dp)
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BeatsColors.Purple)
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 56.dp),
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@SuppressLint("DiscouragedApi")
@Composable
fun AssetImage(
    name: String,
    modifier: Modifier = Modifier,
    placeholder: Color = BeatsColors.Gray,
    alpha: Float = 1f
) {
    val context = LocalContext.current
    val resId = remember(name) {
        val id = context.resources.getIdentifier(name.lowercase(), "drawable", context.packageName)
        // Layoutlib (Compose Preview) hands out fake IDs for unknown names, so verify the
        // resource actually resolves; otherwise fall back to the placeholder.
        if (id != 0 && runCatching { context.resources.getDrawable(id, context.theme) }.isSuccess) id else 0
    }
    Box(modifier.background(placeholder)) {
        if (resId != 0) {
            Image(
                painter = painterResource(resId),
                contentDescription = name,
                contentScale = ContentScale.Crop, // .scaledToFill() + .clipped()
                alpha = alpha,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun BeatsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = if (isPassword) KeyboardType.Password else keyboardType
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = BeatsColors.Gray.copy(alpha = 0.8f)
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = IosType.body.copy(color = Color.White),
        cursorBrush = SolidColor(BeatsColors.Purple),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        decorationBox = { inner ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(background)
                    .padding(16.dp)
            ) {
                if (value.isEmpty()) {
                    Text(placeholder, style = IosType.body, color = Color.White.copy(alpha = 0.6f))
                }
                inner()
            }
        }
    )
}
