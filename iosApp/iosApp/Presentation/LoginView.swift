//
//  LoginView.swift
//  iosApp
//
//  Created by Shii on 04/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel

    @State var mobileNumber = ""
    @State var otp = ""
    @State var showOTP = false
    @State var sentOTP = false
    @State var secondsRemaining = 0
    
    func startTimer() {
        if secondsRemaining > 0 {
            return
        }
        
        if !sentOTP {
            return
        }
        
        secondsRemaining = 60
        
        Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { timer in
            if self.secondsRemaining > 0 {
                self.secondsRemaining -= 1
            } else {
                timer.invalidate()
            }
        }
        .fire()
    }
    
    func onSendOTP() {
        if mobileNumber.count >= 10 && mobileNumber.allSatisfy({ $0.isNumber }) {
            // TODO: Add logic to send OTP to the entered mobile number
            secondsRemaining = 0
            sentOTP = true
            showOTP = true
            startTimer()
        } else {
            // Show error message if mobile number is not valid
            showAlert(message: "Please enter a valid mobile number")
        }
    }
    
    func onResendOTP() {
        if mobileNumber.count >= 10 && mobileNumber.allSatisfy({ $0.isNumber }) {
            // TODO: Add logic to resend OTP to the entered mobile number
            startTimer()
        } else {
            // Show error message if mobile number is not valid
            showAlert(message: "Please enter a valid mobile number")
        }
    }
    
    func onLogin() {
        // TODO: Add logic to validate the entered OTP and perform login
        
        viewModel.login()
    }
    
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            Spacer()
            
            Text("Welcome to MyApp")
                .font(.title)
            
            if showOTP {
                Text("OTP sent to \(mobileNumber)")
                    .font(.headline)
                
                OTPField(otp: $otp, onLoginClick: onLogin)
                
                HStack(spacing: 16) {
                    Button(action: {
                        showOTP = false
                        secondsRemaining = 0
                    }) {
                        Text("Change Number")
                            .foregroundColor(.red)
                    }
                    
                    if secondsRemaining == 0 {
                        Button(action: onResendOTP) {
                            Text("Resend OTP")
                        }
                    } else {
                        Text("Resend OTP in \(secondsRemaining) seconds")
                            .font(.caption)
                            .foregroundColor(.gray)
                            .onReceive(Timer.publish(every: 1, on: .main, in: .common).autoconnect()) { _ in
                                if self.secondsRemaining > 0 {
                                    self.secondsRemaining -= 1
                                }
                            }

                    }
                }
            } else {
                MobileNumberField(mobileNumber: $mobileNumber, onSendClick: onSendOTP)
            }
            
            Spacer()
        }
        .padding(16)
    }
}

struct MobileNumberField: View {
    @Binding var mobileNumber: String
    var onSendClick: () -> Void
    
    var body: some View {
        OutlinedTextFieldWithButton(
            value: $mobileNumber,
            label: "Enter Mobile Number",
            keyboardType: .phonePad,
            buttonText: "Send OTP",
            onButtonClick: onSendClick
        )
    }
}

struct OTPField: View {
    @Binding var otp: String
    var onLoginClick: () -> Void
    
    var body: some View {
        OutlinedTextFieldWithButton(
            value: $otp,
            label: "Enter OTP",
            keyboardType: .numberPad,
            buttonText: "Login",
            onButtonClick: onLoginClick
        )
    }
}

struct OutlinedTextFieldWithButton: View {
    let value: Binding<String>
    let label: String
    let keyboardType: UIKeyboardType
    let buttonText: String
    let onButtonClick: () -> Void
    
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            TextField(label, text: value)
                .keyboardType(keyboardType)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .frame(maxWidth: .infinity)
            
            Button(action: onButtonClick) {
                Text(buttonText)
                    .bold()
                    .foregroundColor(.white)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .background(Color.blue)
            .cornerRadius(8)
        }
    }
}

func showAlert(message: String) {
    let alert = UIAlertController(title: "", message: message, preferredStyle: .alert)
    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
    UIApplication.shared.windows.first?.rootViewController?.present(alert, animated: true, completion: nil)
}
