package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun ZenModeScreen(completedSessions: List<FocusSession> = emptyList(),
                  onSessionAdded: (FocusSession) -> Unit = {}) {

    var selectedSession by remember { mutableStateOf("Study") }
    var hours by remember { mutableStateOf("0") }
    var minutes by remember { mutableStateOf("25") }
    var seconds by remember { mutableStateOf("0") }
    var sessionStarted by remember { mutableStateOf(false) }


    // Calculate total seconds
    val totalSeconds = try {
        (hours.toIntOrNull() ?: 0) * 3600 + (minutes.toIntOrNull() ?: 0) * 60 + (seconds.toIntOrNull() ?: 0)
    } catch (e: Exception) {
        0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Header
        Text(
            text = "Focus Mode 🎯",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )

        if (!sessionStarted) {
            // Session Type Selection
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Select Session Type",
                    fontSize = 14.sp,
                    color = MutedClay,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SessionTypeButton(
                        title = "Study",
                        selected = selectedSession == "Study",
                        onClick = { selectedSession = "Study" },
                        modifier = Modifier.weight(1f)
                    )

                    SessionTypeButton(
                        title = "Work",
                        selected = selectedSession == "Work",
                        onClick = { selectedSession = "Work" },
                        modifier = Modifier.weight(1f)
                    )

                    SessionTypeButton(
                        title = "Gym",
                        selected = selectedSession == "Gym",
                        onClick = { selectedSession = "Gym" },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Time Input
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardBackground, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Set Duration (HH:MM:SS)",
                    fontSize = 14.sp,
                    color = MutedClay,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Hours
                    TimeInputField(
                        value = hours,
                        onValueChange = { hours = it },
                        label = "HH",
                        modifier = Modifier.weight(1f)
                    )

                    Text(":", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DeepGreen)

                    // Minutes
                    TimeInputField(
                        value = minutes,
                        onValueChange = { minutes = it },
                        label = "MM",
                        modifier = Modifier.weight(1f)
                    )

                    Text(":", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DeepGreen)

                    // Seconds
                    TimeInputField(
                        value = seconds,
                        onValueChange = { seconds = it },
                        label = "SS",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Start Button
            Button(
                onClick = {
                    if (totalSeconds > 0) {
                        sessionStarted = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepGreen
                ),
                enabled = totalSeconds > 0
            ) {
                Text(
                    text = "Start Focus Session",
                    fontSize = 16.sp,
                    color = CreamBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // TIMER SCREEN
        if (sessionStarted) {
            TimerRunning(
                sessionType = selectedSession,
                totalSeconds = totalSeconds,
                onSessionEnd = { session ->
                    onSessionAdded(session)
                    sessionStarted = false
                }
            )
        }
    }
}

@Composable
fun TimerRunning(
    sessionType: String,
    totalSeconds: Int,
    onSessionEnd: (FocusSession) -> Unit
) {
    var timeLeftSeconds by remember { mutableStateOf(totalSeconds) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning && timeLeftSeconds > 0) {
            delay(1000)
            timeLeftSeconds--
        }
        if (timeLeftSeconds == 0) {
            isRunning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {


        Text(
            text = "Session Running ⏳",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Big Timer Display
        val h = timeLeftSeconds / 3600
        val m = (timeLeftSeconds % 3600) / 60
        val s = timeLeftSeconds % 60

        Text(
            text = String.format("%02d:%02d:%02d", h, m, s),
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = sessionType,
            fontSize = 16.sp,
            color = MutedClay,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Pause/Resume and Stop Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BurntVienna
                )
            ) {
                Text(
                    text = if (isRunning) "Pause" else "Resume",
                    fontSize = 14.sp,
                    color = CreamBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {
                    val timeSpent = totalSeconds - timeLeftSeconds
                    val timeSpentMinutes = timeSpent / 60
                    val session = FocusSession(
                        sessionType = sessionType,
                        durationMinutes = totalSeconds / 60,
                        timeSpentMinutes = timeSpentMinutes,
                        completedAt = getCurrentTime()
                    )
                    onSessionEnd(session)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BurntVienna
                )
            ) {
                Text(
                    text = "Stop",
                    fontSize = 14.sp,
                    color = CreamBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun SessionTypeButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) DeepGreen else CardBackground
        )
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            color = if (selected) CreamBackground else DeepGreen,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || (newValue.toIntOrNull() != null && newValue.toInt() >= 0)) {
                onValueChange(newValue)
            }
        },
        label = { Text(label, fontSize = 12.sp) },
        modifier = modifier
            .height(56.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CreamBackground,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = BurntVienna,
            unfocusedIndicatorColor = MutedClay
        ),
        textStyle = androidx.compose.material3.LocalTextStyle.current.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    )
}