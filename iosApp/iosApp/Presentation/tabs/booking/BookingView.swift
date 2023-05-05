//
//  BookingView.swift
//  iosApp
//
//  Created by Shii on 05/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct BookingView: View {
    var body: some View {
        VStack {
            Text("Booking")
            
            Divider()
            
            Spacer()
            
            VStack {
                Spacer()
                
                Text("Additional Booking Info")
                
                Spacer()
                
                NavigationLink(destination: BookingDetailView()) {
                    Text("View Details")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(8)
                }
                .buttonStyle(PlainButtonStyle())
                
                Spacer()
            }
            .navigationTitle("Booking Screen")
            
            Spacer()

            Divider()
        }
    }
}

struct BookingView_Previews: PreviewProvider {
    static var previews: some View {
        BookingView()
    }
}
