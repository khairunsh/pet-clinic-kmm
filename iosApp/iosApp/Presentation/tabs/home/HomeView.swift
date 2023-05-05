//
//  HomeView.swift
//  iosApp
//
//  Created by Shii on 05/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        VStack {
            Text("Home")
            
            Divider()
            
            Spacer()
            
            VStack {
                Spacer()
                
                Text("Additional Home Info")
                
                Spacer()
                
                NavigationLink(destination: HomeDetailView()) {
                    Text("View Details")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                }
                .buttonStyle(PlainButtonStyle())
                
                Spacer()
            }
            .navigationTitle("Home Screen")
            
            Spacer()

            Divider()
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
