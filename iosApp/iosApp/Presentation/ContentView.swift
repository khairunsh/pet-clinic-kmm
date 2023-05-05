import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var loginViewModel = LoginViewModel()
    
    @State private var selection = 0
    
    var body: some View {
        Group {
            if loginViewModel.isLoggedIn {
                NavigationView {
                    TabView(selection: $selection) {
                        HomeView()
                            .tabItem {
                                Label("Home", systemImage: "house.fill")
                            }
                            .tag(0)
                        
                        BookingView()
                            .tabItem {
                                Label("Booking", systemImage: "calendar")
                            }
                            .tag(1)
                        
                        ProfileView()
                            .tabItem {
                                Label("Profile", systemImage: "person.fill")
                            }
                            .tag(2)
                    }
                }
            } else {
                LoginView(viewModel: loginViewModel)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        ContentView()
	}
}
