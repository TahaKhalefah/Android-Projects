//
//  TabBar.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct TabBar: View {
    var body: some View {
        
        TabView {
            HomeScreen()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Home")
                }
            
            ExploreScreen()
                .tabItem {
                    Image(systemName: "magnifyingglass")
                    Text("Explore")
                }
            
            CartScreen()
                .tabItem {
                    Image(systemName: "cart.fill")
                    Text("Cart")
                }
            
            Offer_Screen()
                .tabItem {
                    Image(systemName: "person.crop.circle")
                    Text("Profile")
                    
                }
       
                HomeProfileView()
                .tabItem {
                    Image(systemName: "person.crop.circle")
                    Text("Profile")
                    
                }
        }    }
}

struct TabBar_Previews: PreviewProvider {
    static var previews: some View {
        TabBar()
    }
}
