package com.vandita.zenith

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Your color palette from documentation
val CreamBackground = Color(0xFFF9F7F2)
val DeepGreen = Color(0xFF2D4030)
val MutedClay = Color(0xFF8C7E6D)
val BurntVienna = Color(0xFFB76240)
val CardBackground = Color(0xFFEDE7DD)

@Composable
fun PermissionScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Emoji icon
        Text(
            text = "📊",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Zenith", //APP NAME
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tagline — updated
        Text(
            text = "Know yourself. Own your time.",
            fontSize = 16.sp,
            color = MutedClay
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Info card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackground, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Text(
                text = "Before we begin 👋",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DeepGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "To show you how you spend your time, Zenith needs to see which apps you use. Think of it like a diary — only you can read it. Nothing ever leaves your phone.",
                fontSize = 14.sp,
                color = MutedClay,
                textAlign = TextAlign.Start,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Grant button
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepGreen
            )
        ) {
            Text(
                text = "Grant Access →",
                fontSize = 16.sp,
                color = CreamBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tap the button, find Zenith in the list,\nand turn on the switch. Then come back here!",
            fontSize = 12.sp,
            color = MutedClay,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}