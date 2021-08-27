//
//  Login.swift
//  Sell3a
//
//  Created by Mnem on 15/06/2021.
//

import SwiftUI
import Firebase
import GoogleSignIn

struct Login: View {
    
    @State var selection: Int? = 0
    @State var selectionGoogle: Int? = 0
    @StateObject private var loginVM = LoginViewModel()
    @State private var showingAlert = false
    @State private var alertMessage : String = ""
    @State private var name = UserDefaults.standard.string(forKey: defaultsKeys.loginName) ?? ""
    @State private var email =  UserDefaults.standard.string(forKey: defaultsKeys.loginEmail) ?? ""
    @State private var photo =  UserDefaults.standard.string(forKey: defaultsKeys.loginPhoto) ?? ""
    
    
    let defaults = UserDefaults.standard
    
    var body: some View {
        
        if(loginVM.logined || loginVM.loginedGoogle){
            TabBar()
        }else{
            ZStack{
                VStack {
                    Image("ic_launcher-playstore")
                        .resizable()
                        .frame(width: 72, height: 72)
                        .padding(.bottom)
                    
                    Text("Welcome to Sell3a")
                        .bold()
                        .font(.system(size: 16))
                        .foregroundColor(neutralDark)
                        .padding(.bottom)
                    
                    Text("Sign in to continue")
                        .font(.system(size: 12))
                        .foregroundColor(primaryGray)
                        .padding(.bottom)
                    
                    CustomTextField(text: $loginVM.email, placeholder: "Email", img: "envelope")
                        .autocapitalization(.none)
                    PasswordTextField(text: $loginVM.password, placeholder: "Password", img: "lock")
                        .padding(.bottom)
                    
                    
                    
                    //                    NavigationLink(destination: TabBar(), tag: 1, selection: $selection) {
                    
                    Button(action: {
                        if loginVM.email.isEmpty {
                            alertMessage = "Please, Enter your email !"
                            showingAlert = true
                        }else if loginVM.password.isEmpty{
                            alertMessage = "Please, Enter your password !"
                            showingAlert = true
                        }else{
                            loginVM.Login()
                            
                        }
                        selection = 1
                        
                    }) {
                        Text("Sign In")
                            .bold()
                            .font(.system(size: 14))
                            .foregroundColor(.white)
                            .frame(width: 343, height: 47)
                    }
                    .background(primaryBlue)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(primaryBlue, lineWidth: 5)
                    )
                    .alert(isPresented: $showingAlert) {
                        Alert(title: Text("Oops!"), message: Text(alertMessage), dismissButton: .default(Text("Got it!")))
                    }
                    
                    //                    }
                    
                    Text("OR")
                        .bold()
                        .padding()
                        .frame(width: 100, height: 50)
                        .font(.system(size: 14))
                        .foregroundColor(primaryGray)
                    
                    //                    NavigationLink(destination: TabBar(), tag: 1, selection: $selectionGoogle) {
                    Button(action: {
                        GIDSignIn.sharedInstance().presentingViewController =
                            UIApplication.shared.windows.first?.rootViewController
                        GIDSignIn.sharedInstance()?.signIn()
                        
                        name = Auth.auth().currentUser?.email ?? "user"
                        email = Auth.auth().currentUser?.displayName ?? "user email"
                        //                          let  photo = URL(string: Auth.auth().currentUser!.photoURL)!
                        
                        print(name)
                        print(email)
                        
                        defaults.set(name, forKey: defaultsKeys.loginName)
                        defaults.set(email, forKey: defaultsKeys.loginEmail)
                        //                            defaults.set(photo, forKey: defaultsKeys.loginPhoto)
                        
                        selectionGoogle = 1
                        
                    }) {
                        Image("google")
                            .renderingMode(.original)
                            .resizable()
                            .padding()
                        
                        Text("Login with Google")
                            .bold()
                            .font(.system(size: 14))
                            .foregroundColor(primaryGray)
                            .frame(width: 243, height: 57)
                    }
                    .padding()
                    .frame(width: 343, height: 57)
                    .shadow(color: .white, radius: 5)
                    .overlay(
                        RoundedRectangle(cornerRadius: 16)
                            .stroke(shadowColor, lineWidth: 1)
                    )
                    //                    }
                    
                    //                    Button(action: {
                    //
                    //                    }) {
                    //                        Image("facebook")
                    //                            .renderingMode(.original)
                    //                            .resizable()
                    //                            .padding()
                    //
                    //                        Text("Login with Facebook")
                    //                            .bold()
                    //                            .font(.system(size: 14))
                    //                            .foregroundColor(primaryGray)
                    //                            .frame(width: 243, height: 57)
                    //                    }
                    //
                    //                    .padding()
                    //                    .frame(width: 343, height: 57)
                    //                    .shadow(color: .white, radius: 5)
                    //                    .overlay(
                    //                        RoundedRectangle(cornerRadius: 16)
                    //                            .stroke(shadowColor, lineWidth: 1)
                    //                    )
                    
                    VStack {
                        //                        Text("Forgot Password?")
                        //                            .foregroundColor(primaryBlue)
                        //                            .bold()
                        //                            .padding()
                        //                            .font(.system(size: 14))
                        
                        HStack {
                            Text("Don’t have a account?")
                                .font(.system(size: 14))
                            
                            NavigationLink(destination: Register()) {
                                Text("Register")
                                    .font(.system(size: 14))
                                    .foregroundColor(primaryBlue)
                                    .bold()
                            }
                            
                        }
                    }
                }
            }
            .navigationBarHidden(true)
            //            .onAppear(perform: {
            //
            //                if (name == "user")
            //                {
            //                    loginVM.loginedGoogle = true
            //                    selectionGoogle = 1
            //                }else{
            //                    print("bazet")
            //                }
            //            })
        }
        
    }
    
    
    
    struct Login_Previews: PreviewProvider {
        static var previews: some View {
            Login()
        }
    }
}
