package com.shii.petclinic.android.presentation.tabs.booking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun BookingDetailsScreen() {
    Text(
        text = "Booking Details Screen",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center,
    )
}
