package com.vandita.zenith

import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.compose.ui.graphics.Color
import java.util.Calendar

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
            stat.totalTimeInForeground > 60000 &&
                    stat.packageName !in hiddenPackages &&
                    stat.packageName != context.packageName &&
                    !stat.packageName.startsWith("com.example")
        }

        // Group same apps together
        .groupBy { it.packageName }

        // Sum their times
        .mapValues { (_, group) ->
            group.sumOf { it.totalTimeInForeground }
        }

        .map { (packageName, totalTime) ->

            val appName = try {

                pm.getApplicationLabel(
                    pm.getApplicationInfo(packageName, 0)
                ).toString()

            } catch (e: Exception) {

                packageName
                    .split(".")
                    .last()
                    .replaceFirstChar { it.uppercase() }
            }

            AppUsageInfo(
                appName = appName,
                packageName = packageName,
                totalMinutes = totalTime / 60000
            )
        }

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
            Color(0xFF6A7E3F) // olive green
        )

        totalMinutes < 240 -> Triple(
            "Moderate",
            "You're doing okay. Try to take a few breaks.",
            Color(0xFFB76240) // burnt vienna
        )

        else -> Triple(
            "Distracted",
            "Heavy screen day. Maybe time for a break?",
            Color(0xFFD96868) // alert red
        )
    }
}