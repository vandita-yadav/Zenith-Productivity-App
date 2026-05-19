package com.vandita.zenith

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@Composable
fun ZenithHeader(
    title: String,
    iconRes: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepGreen)
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            ),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = CreamBackground,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                color = CreamBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CreamBackground.copy(alpha = 0.15f)),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = CreamBackground
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CreamBackground.copy(alpha = 0.15f)),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = CreamBackground
                )
            }
        }
    }
}