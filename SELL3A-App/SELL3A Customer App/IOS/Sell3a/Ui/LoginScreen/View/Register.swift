//
//  Register.swift
//  Sell3a
//
//  Created by Mnem on 15/06/2021.
//

import SwiftUI

struct Register: View {
    
    @StateObject private var registerVMM = RigesterViewModel()
    @State private var password: String = ""
    @State private var showingAlert = false
    @State private var alertMessage : String = ""
    
    var body: some View {
        NavigationView{
            ZStack{
                VStack {
                    Image("sel3a")
                        .resizable()
                        .frame(width: 72, height: 72)
                        .padding(.bottom)
                    
                    Text("Let’s Get Started")
                        .bold()
                        .font(.system(size: 16))
                        .foregroundColor(neutralDark)
                        .padding(.bottom)
                    
                    Text("Create an new account")
                        .font(.system(size: 12))
                        .foregroundColor(primaryGray)
                        .padding(.bottom)
                    
                    CustomTextField(text: $registerVMM.name, placeholder: "Full Name", img: "person")
                    CustomTextField(text: $registerVMM.email, placeholder: "Your Email", img: "envelope")   .keyboardType(.emailAddress)
                    PasswordTextField(text: $registerVMM.password, placeholder: "Password", img: "lock")
                    PasswordTextField(text: $password, placeholder: "Password Again", img: "lock")
                        .padding(.bottom)
                    
                    
                    Button(action: {
                        if registerVMM.name.isEmpty {
                            alertMessage = "Please, Enter your name !"
                            showingAlert = true
                        }else if registerVMM.email.isEmpty{
                            alertMessage = "Please, Enter your email !"
                            showingAlert = true
                        }else if registerVMM.password.isEmpty{
                            showingAlert = true
                            alertMessage = "Please, Enter your password !"
                        }else if password != registerVMM.password{
                            showingAlert = true
                            alertMessage = "password missmatch!"
                        }else{
                            registerVMM.Register()
                        }
                        
                    }) {
                        Text("Sign Up")
                            .bold()
                            .font(.system(size: 14))
                            .foregroundColor(.white)
                            .frame(width: 343, height: 57)
                    }
                    .background(primaryBlue)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(primaryBlue, lineWidth: 5)
                    )
                    .padding(.bottom)
                    .alert(isPresented: $showingAlert) {
                        Alert(title: Text("Oops!"), message: Text(alertMessage), dismissButton: .default(Text("Got it!")))
                    }
                    
                    
                    HStack {
                        Text("have a account?")
                            .font(.system(size: 14))
                        
                        NavigationLink(destination: Login()) {
                            Text("Sign In")
                                .font(.system(size: 14))
                                .foregroundColor(primaryBlue)
                                .bold()
                        }
                        
                    }
                }
            }
        }
    }
    
    struct Register_Previews: PreviewProvider {
        static var previews: some View {
            Register()
        }
    }
}
