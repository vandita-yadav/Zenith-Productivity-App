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

    val appInfoList = mutableListOf<AppUsageInfo>()

    for (stat in stats) {
        if (stat.totalTimeInForeground < 60000) continue  // Skip < 1 min
        if (stat.packageName in hiddenPackages) continue  // Skip system apps
        if (stat.packageName == context.packageName) continue  // Skip Zenith itself

        try {
            val appInfo = pm.getApplicationInfo(stat.packageName, 0)
            val appName = pm.getApplicationLabel(appInfo).toString()

            // Only add if we got a real app name (not package name)
            if (!appName.contains(".") && appName.isNotEmpty() && appName.length < 50) {
                appInfoList.add(
                    AppUsageInfo(
                        appName = appName,
                        packageName = stat.packageName,
                        totalMinutes = stat.totalTimeInForeground / 60000
                    )
                )
            }
        } catch (e: Exception) {
            // Skip apps we can't get name for
            continue
        }
    }

    return appInfoList
        .sortedByDescending { it.totalMinutes }
        .take(7)
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