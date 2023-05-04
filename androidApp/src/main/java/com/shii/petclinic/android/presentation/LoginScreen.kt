package com.shii.petclinic.android.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun LoginScreen(context: Context, onLoginSuccessfully: () -> Unit) {
    var mobileNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var showOTP by remember { mutableStateOf(false) }
    var sentOTP by remember { mutableStateOf(false) }
    var secondsRemaining by remember { mutableStateOf(0L) }

    fun startTimer() {
        if (secondsRemaining > 0L) {
            return
        }

        secondsRemaining = 60L

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (secondsRemaining > 0) {
                        secondsRemaining -= 1
                        handler.postDelayed(this, 1000)
                    } else {
                        handler.removeCallbacks(this)
                    }
                }
            },
            1000,
        )
    }

    fun onSendOTP() {
        if (mobileNumber.length >= 10 && mobileNumber.isDigitsOnly()) {
            // TODO: Add logic to send OTP to the entered mobile number
            sentOTP = true
            showOTP = true
            startTimer()
        } else {
            // Show error message if mobile number is not valid
            showToast("Please enter a valid mobile number", context)
        }
    }

    fun onResendOTP() {
        if (mobileNumber.length >= 10 && mobileNumber.isDigitsOnly()) {
            // TODO: Add logic to resend OTP to the entered mobile number
            startTimer()
        } else {
            // Show error message if mobile number is not valid
            showToast("Please enter a valid mobile number", context)
        }
    }

    fun onLogin() {
        // TODO: Add logic to validate the entered OTP and perform login
        onLoginSuccessfully()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Welcome to MyApp", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        if (showOTP) {
            Text(
                "OTP sent to $mobileNumber",
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(modifier = Modifier.height(16.dp))
            OTPField(
                otp = otp,
                onOTPChange = { otp = it },
                onLoginClick = { onLogin() },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                OutlinedButton(onClick = {
                    showOTP = false
                    secondsRemaining = 0L
                }) {
                    Text("Change Number")
                }
                if (secondsRemaining == 0L) {
                    OutlinedButton(onClick = { onResendOTP() }) {
                        Text("Resend OTP")
                    }
                } else {
                    Text(
                        "Resend OTP in $secondsRemaining seconds",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        } else {
            MobileNumberField(
                mobileNumber = mobileNumber,
                onMobileNumberChange = { mobileNumber = it },
                onSendClick = { onSendOTP() },
            )
        }
    }
}

@Composable
fun MobileNumberField(
    mobileNumber: String,
    onMobileNumberChange: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    OutlinedTextFieldWithButton(
        value = mobileNumber,
        onValueChange = onMobileNumberChange,
        label = "Enter Mobile Number",
        keyboardType = KeyboardType.Phone,
        buttonText = "Send OTP",
        onButtonClick = onSendClick,
    )
}

@Composable
fun OTPField(
    otp: String,
    onOTPChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    OutlinedTextFieldWithButton(
        value = otp,
        onValueChange = onOTPChange,
        label = "Enter OTP",
        keyboardType = KeyboardType.Number,
        buttonText = "Login",
        onButtonClick = onLoginClick,
    )
}

@Composable
fun OutlinedTextFieldWithButton(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(buttonText)
        }
    }
}

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
