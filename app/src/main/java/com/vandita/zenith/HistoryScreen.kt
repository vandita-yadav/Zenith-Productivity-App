package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "History Screen",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen
        )
    }
}