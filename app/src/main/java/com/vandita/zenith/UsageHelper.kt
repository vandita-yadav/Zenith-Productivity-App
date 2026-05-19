package com.vandita.zenith

import android.content.Context
import android.content.pm.PackageManager
import android.app.usage.UsageStatsManager
import java.util.Calendar
import androidx.compose.ui.graphics.Color

data class AppUsageInfo(
    val appName: String,
    val packageName: String,
    val totalMinutes: Long
)

// System apps to hide — they're not useful to the user
private val hiddenPackages = setOf(
    "com.google.android.apps.nexuslauncher",
    "com.android.launcher",
    "com.android.launcher3",
    "com.miui.home",
    "com.oneplus.launcher",
    "com.sec.android.app.launcher",
    "com.android.systemui",
    "com.google.android.inputmethod.latin",
    "com.android.settings",
    "com.google.android.googlequicksearchbox",
    "android",
    "com.android.phone",
    "com.google.android.gms"
)

fun getTopApps(context: Context): List<AppUsageInfo> {
    val usageStatsManager = context.getSystemService(
        Context.USAGE_STATS_SERVICE
    ) as UsageStatsManager

    val calendar = Calendar.getInstance()
    val endTime = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val startTime = calendar.timeInMillis

    val stats = usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        startTime,
        endTime
    )

    val pm = context.packageManager

    return stats
        .filter { stat ->
            stat.totalTimeInForeground > 60000  // minimum 1 minute — removes 0m apps
                    && stat.packageName !in hiddenPackages  // removes system/launcher apps
                    && stat.packageName != context.packageName  // removes Zenith itself
                    && !stat.packageName.startsWith("com.example")
        }
        .sortedByDescending { it.totalTimeInForeground }
        .take(7)
        .map { stat ->
            val appName = try {
                pm.getApplicationLabel(
                    pm.getApplicationInfo(stat.packageName, 0)
                ).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                // If name not found, skip it by using package name
                // We'll filter these out below
                stat.packageName
            }
            AppUsageInfo(
                appName = appName,
                packageName = stat.packageName,
                totalMinutes = stat.totalTimeInForeground / 60000
            )
        }
        .filter { app ->
            // Remove anything that still shows raw package name format
            !app.appName.contains(".")
                    || app.appName.length < 30
        }
}

fun getTotalScreenMinutes(apps: List<AppUsageInfo>): Long {
    return apps.sumOf { it.totalMinutes }
}

fun getHealthScore(totalMinutes: Long): Triple<String, String, Color> {
    return when {
        totalMinutes < 120 -> Triple(
            "Focused",
            "Great job! You're having a productive day.",
            Color(0xFF6A7E3F)  // olive green
        )
        totalMinutes < 240 -> Triple(
            "Moderate",
            "You're doing okay. Try to take a few breaks.",
            Color(0xFFB76240)  // burnt vienna
        )
        else -> Triple(
            "Distracted",
            "Heavy screen day. Maybe time for a break?",
            Color(0xFFD96868)  // alert red
        )
    }
}