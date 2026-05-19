package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@Composable
fun DashboardScreen() {
    val context = LocalContext.current

    // Load real usage data from phone
    val appList = remember { getTopApps(context) }
    val totalMinutes = remember { getTotalScreenMinutes(appList) }
    val (scoreLabel, scoreMessage, scoreColor) = remember {
        getHealthScore(totalMinutes)
    }

    val today = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        .format(Date())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Header
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Good day 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen
            )
            Text(
                text = today,
                fontSize = 14.sp,
                color = MutedClay
            )
        }

        // Health Score Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(scoreColor, RoundedCornerShape(20.dp))
                    .padding(24.dp)
            ) {
                Text(
                    text = "Today's Focus",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = scoreLabel,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = scoreMessage,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Total screen time: ${totalMinutes / 60}h ${totalMinutes % 60}m",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // STATISTICS SECTION
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📊 Statistics",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen
            )
        }

        // KPI Cards Row 1
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KPICard(
                    title = "Total Time",
                    value = "${totalMinutes / 60}h",
                    subtitle = "${totalMinutes % 60}m today",
                    backgroundColor = CardBackground,
                    valueColor = DeepGreen,
                    modifier = Modifier.weight(1f)
                )

                KPICard(
                    title = "Apps Used",
                    value = "${appList.size}",
                    subtitle = "in Top 7",
                    backgroundColor = Color(0xFFFFE5CC),
                    valueColor = BurntVienna,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // KPI Cards Row 2
        item {
            if (appList.isNotEmpty()) {
                val mostUsedApp = appList.first()
                val percentage = if (totalMinutes > 0) {
                    ((mostUsedApp.totalMinutes * 100) / totalMinutes).toInt()
                } else 0

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    KPICard(
                        title = "Most Used",
                        value = mostUsedApp.appName.take(12),
                        subtitle = "$percentage% of time",
                        backgroundColor = Color(0xFFE8F5E9),
                        valueColor = Color(0xFF6A7E3F),
                        modifier = Modifier.weight(1f)
                    )

                    KPICard(
                        title = "Avg Per App",
                        value = "${if (appList.isNotEmpty()) totalMinutes / appList.size else 0}m",
                        subtitle = "across apps",
                        backgroundColor = Color(0xFFF3E5F5),
                        valueColor = Color(0xFF7B3FF2),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // View Insights Button
        item {
            Button(
                onClick = {
                    val heavyUsageApp = appList.find { it.totalMinutes >= 120 }
                    if (heavyUsageApp != null) {
                        showFocusNotification(
                            context = context,
                            appName = heavyUsageApp.appName,
                            minutes = heavyUsageApp.totalMinutes
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepGreen
                )
            ) {
                Text(
                    text = "View Insights",
                    color = CreamBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Top Apps Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Top Apps Today",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DeepGreen
            )
            Text(
                text = "Apps that used most of your time",
                fontSize = 13.sp,
                color = MutedClay
            )
        }

        // App list — real data from your phone
        if (appList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No app usage found for today yet.\nCheck back after using your phone a bit!",
                        color = MutedClay,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            items(appList) { app ->
                AppUsageCard(app = app, maxMinutes = appList.first().totalMinutes)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun AppUsageCard(app: AppUsageInfo, maxMinutes: Long) {
    val barProgress = if (maxMinutes > 0) {
        app.totalMinutes.toFloat() / maxMinutes.toFloat()
    } else 0f

    val hours = app.totalMinutes / 60
    val minutes = app.totalMinutes % 60
    val timeText = if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"

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
                text = app.appName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = DeepGreen
            )
            Text(
                text = timeText,
                fontSize = 14.sp,
                color = BurntVienna,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Usage bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(Color(0xFFD9D3CB), RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(barProgress)
                    .height(6.dp)
                    .background(BurntVienna, RoundedCornerShape(3.dp))
            )
        }
    }
}