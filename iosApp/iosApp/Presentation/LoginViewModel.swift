//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Shii on 05/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

class LoginViewModel: ObservableObject {
    @Published var isLoggedIn: Bool

    init() {
        isLoggedIn = UserDefaults.standard.bool(forKey: "isLoggedIn") 
    }

    func login() {
        // Perform login logic here
        UserDefaults.standard.set(true, forKey: "isLoggedIn")
        isLoggedIn = true
    }

    func logout() {
        // Perform logout logic here
        UserDefaults.standard.set(false, forKey: "isLoggedIn")
        isLoggedIn = false
    }
}
