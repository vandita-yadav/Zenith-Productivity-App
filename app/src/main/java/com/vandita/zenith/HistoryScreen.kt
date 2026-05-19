package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen(completedSessions: List<FocusSession> = emptyList()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(20.dp)
    ) {

        Text(
            text = "Session History 📈",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (completedSessions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No sessions completed yet.\nStart a focus session to see history!",
                    fontSize = 16.sp,
                    color = MutedClay,
                    modifier = Modifier.padding(20.dp)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(completedSessions) { session ->
                    SessionHistoryCard(session)
                }
            }
        }
    }
}

@Composable
fun SessionHistoryCard(session: FocusSession) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = session.sessionType,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen
            )

            Text(
                text = session.completedAt,
                fontSize = 12.sp,
                color = MutedClay
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Duration: ${session.durationMinutes} min",
                fontSize = 14.sp,
                color = MutedClay
            )

            Text(
                text = "Spent: ${session.timeSpentMinutes} min",
                fontSize = 14.sp,
                color = BurntVienna,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}