package com.vandita.zenith

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.graphics.drawable.Drawable

data class AppUsageInfo(
    val appName: String,
    val packageName: String,
    val totalMinutes: Long,
    val appIcon: Drawable? = null
)

data class FocusSession(
    val sessionType: String,      // "Study", "Work", "Gym"
    val durationMinutes: Int,      // Total duration (e.g., 25, 45, 60)
    val timeSpentMinutes: Int,     // How much time actually used before stopping
    val completedAt: String        // Timestamp when session ended
)

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return sdf.format(Date())
}