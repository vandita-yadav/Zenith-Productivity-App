package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepGreen)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = CreamBackground
                )
            }
            Text(
                text = "About Zenith",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CreamBackground,
                modifier = Modifier.weight(1f)
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Zenith",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen
            )

            Text(
                text = "Your Personal Focus Coach",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("What is Zenith?")
            SectionText(
                "Zenith is a privacy-first app that helps you understand and improve your screen time habits. " +
                        "Unlike other apps, Zenith keeps all your data on your phone — we never send it anywhere."
            )

            SectionTitle("Key Features")
            BulletPoint("▪️ Real-time usage tracking of your top apps")
            BulletPoint("▪️ Focus Mode with custom timers (Study, Work, Gym)")
            BulletPoint("▪️ Session history to track your progress")
            BulletPoint("▪️ 100% private — your data never leaves your phone")
            BulletPoint("▪️ Beautiful, minimal design")

            SectionTitle("How It Works")
            SectionText(
                "1. Grant usage access permission\n" +
                        "2. View your real app usage on the Dashboard\n" +
                        "3. Start Focus sessions to stay on track\n" +
                        "4. Check your History to see progress"
            )

            SectionTitle("Privacy")
            SectionText(
                "Zenith is completely privacy-first. All processing happens on your device. " +
                        "We don't collect, store, or transmit any of your personal data."
            )

            SectionTitle("Version")
            SectionText("Zenith v1.0\nDeveloped as a college project\nSession 2025-26")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackClick,  // Call the onBackClick function
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DeepGreen)
            ) {
                Text("Close", color = CreamBackground, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = DeepGreen
    )
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = MutedClay,
        lineHeight = 20.sp
    )
}

@Composable
fun BulletPoint(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = MutedClay,
        modifier = Modifier.padding(start = 8.dp)
    )
}