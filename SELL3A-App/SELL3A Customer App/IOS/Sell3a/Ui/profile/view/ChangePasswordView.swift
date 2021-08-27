//
//  CjangePasswordView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct ChangePasswordView: View {
    @State private var old_pass = ""
    @State private var new_pass = ""
    @State private var new_pass_again = ""
    @State private var showingAlert = false
    @State private var alertMessage : String = ""
    @State private var selection: Int? = 0
    @StateObject private var passVM = PasswordViewModel()

    
    var body: some View {
        NavigationView{
        VStack{
            Text("Old Password")
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20)
                .foregroundColor(colorDarkGray)
                .font(.system(size: 20, weight: .heavy, design: .default))
                .padding(.top,20)
            
            PasswordTextField(text: $old_pass, placeholder: "*******", img: "lock")
            
            Text("New Password")
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20)
                .foregroundColor(colorDarkGray)
                .font(.system(size: 20, weight: .heavy, design: .default))
                .padding(.top,20)
            
            PasswordTextField(text: $new_pass, placeholder: "*******", img: "lock")
            
            Text("New Password Again")
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20)
                .foregroundColor(colorDarkGray)
                .font(.system(size: 20, weight: .heavy, design: .default))
                .padding(.top,20)
            
            PasswordTextField(text: $new_pass_again, placeholder: "*******", img: "lock")
            
            Spacer()
            
            NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selection) {
                Button(action: {
                    if(old_pass == "123456"){
                        if (new_pass == new_pass_again) {
                            alertMessage = "Password Changed !"
                            showingAlert = true
                            passVM.changePassword(request: PasswordRequest(email: "hagersamir47@gmail.com", old_password: old_pass, new_password: new_pass))
                            selection = 1

                        }else{
                            alertMessage = "password miss match !"
                            showingAlert = true
                        }
                    }else {
                        alertMessage = "old passeord is not correct !!"
                        showingAlert = true
                    }
                    
                }) {
                    Text("Save")
                        .bold()
                        .font(.system(size: 14))
                        .foregroundColor(.white)
                        .frame(width: 343, height: 57)
                }
                .padding(.bottom)
                .background(primaryBlue)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(primaryBlue, lineWidth: 5)
                )
                .alert(isPresented: $showingAlert) {
                    Alert(title: Text("Oops!"), message: Text(alertMessage), dismissButton: .default(Text("Got it!")))
                }
            }
        }
    }
//        .navigationBarHidden(true)
}
}
struct ChangePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        ChangePasswordView()
    }
}
