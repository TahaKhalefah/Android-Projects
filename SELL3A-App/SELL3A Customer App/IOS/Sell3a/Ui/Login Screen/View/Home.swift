//
//  Home.swift
//  Sell3a
//
//  Created by Mnem on 15/06/2021.
//

import SwiftUI
import GoogleSignIn
import Firebase

struct Home: View {
    var body: some View {
        VStack{
        
            
            Text("logged in as \n\(Auth.auth().currentUser!.email!)").multilineTextAlignment(.center)
        
            Button(action: {
                logOut()
            }) {
                Image("google")
                    .renderingMode(.original)
                    .resizable()
                    .padding()
                
                Text("Logout Google")
                    .bold()
                    .font(.system(size: 14))
                    .foregroundColor(primaryGray)
                    .frame(width: 243, height: 57)
            }
        
        }
    }
}

func logOut() {
    GIDSignIn.sharedInstance()?.signOut()
    try! Auth.auth().signOut()
    
}

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        Home()
    }
}
