package com.vandita.zenith

sealed class Screen {
    abstract val route: String

    object Dashboard : Screen() {
        override val route: String = "dashboard"
    }

    object ZenMode : Screen() {
        override val route: String = "zen_mode"
    }

    object History : Screen() {
        override val route: String = "history"
    }

    object About : Screen() {
        override val route: String = "about"
    }
}