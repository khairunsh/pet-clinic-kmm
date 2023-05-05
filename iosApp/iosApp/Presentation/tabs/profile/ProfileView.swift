//
//  ProfileView.swift
//  iosApp
//
//  Created by Shii on 05/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    var body: some View {
        VStack {
            Text("Profile")
            
            Divider()
            
            Spacer()
            
            VStack {
                Spacer()
                
                Text("Additional Profile Info")
                
                Spacer()
                
                NavigationLink(destination: ProfileDetailView()) {
                    Text("View Details")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                }
                .buttonStyle(PlainButtonStyle())
                
                Spacer()
            }
            .navigationTitle("Profile Screen")
            
            Spacer()

            Divider()
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
