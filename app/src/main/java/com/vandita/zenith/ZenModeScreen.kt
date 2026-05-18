package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ZenModeScreen() {

    var selectedSession by remember { mutableStateOf("Study") }
    var selectedDuration by remember { mutableStateOf(25) }
    var sessionStarted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Focus Mode 🎯",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose your session type",
            fontSize = 15.sp,
            color = MutedClay
        )

        Spacer(modifier = Modifier.height(40.dp))

        SessionButton(
            title = "Study",
            selected = selectedSession == "Study",
            onClick = {
                selectedSession = "Study"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SessionButton(
            title = "Work",
            selected = selectedSession == "Work",
            onClick = {
                selectedSession = "Work"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SessionButton(
            title = "Gym",
            selected = selectedSession == "Gym",
            onClick = {
                selectedSession = "Gym"
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Choose duration",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = DeepGreen
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            DurationButton(
                minutes = 25,
                selected = selectedDuration == 25,
                onClick = {
                    selectedDuration = 25
                }
            )

            DurationButton(
                minutes = 45,
                selected = selectedDuration == 45,
                onClick = {
                    selectedDuration = 45
                }
            )

            DurationButton(
                minutes = 60,
                selected = selectedDuration == 60,
                onClick = {
                    selectedDuration = 60
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        if(!sessionStarted)
        {
            Text(
                text = "Selected: $selectedSession • $selectedDuration min",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = BurntVienna
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { sessionStarted = true },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepGreen
                )

            ) {

                Text(
                    text = "Start Focus Session",
                    fontSize = 17.sp,
                    color = CreamBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (sessionStarted) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Focus Session Running ⏳",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepGreen
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$selectedSession • $selectedDuration minutes",
                    fontSize = 18.sp,
                    color = BurntVienna
                )
            }
        }
    }
}

@Composable
fun SessionButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),

        shape = RoundedCornerShape(16.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor = if (selected)
                DeepGreen
            else
                CardBackground
        )

    ) {

        Text(
            text = title,
            fontSize = 18.sp,
            color = if (selected)
                CreamBackground
            else
                DeepGreen
        )
    }
}


@Composable
fun DurationButton(
    minutes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected)
                BurntVienna
            else
                CardBackground
        )
    ) {

        Text(
            text = "${minutes}m",
            color = if (selected)
                CreamBackground
            else
                DeepGreen
        )
    }
}