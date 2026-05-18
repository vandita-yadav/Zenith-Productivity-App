package com.vandita.zenith

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")

    object ZenMode : Screen("zen_mode")

    object History : Screen("history")
}