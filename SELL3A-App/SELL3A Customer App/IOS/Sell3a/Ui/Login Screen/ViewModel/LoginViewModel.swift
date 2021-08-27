//
//  LoginViewModel.swift
//  Sell3a
//
//  Created by Mnem on 25/06/2021.
//

import Foundation

class LoginViewModel: ObservableObject {
    
    var webServices = ApiService.shared
    var email: String = ""
    var password: String = ""
    let defaults = UserDefaults.standard
    @Published var logined : Bool = UserDefaults.standard.bool(forKey: defaultsKeys.isLogined)
    @Published var loginedGoogle : Bool = UserDefaults.standard.bool(forKey: defaultsKeys.isLoginedGoogle)

    func Login(){
        webServices.callingLoginAPI(login: LoginModel.init(email: email, password: password)) { isTrue in
            
            if isTrue {
                print("succes")
              
                self.logined = true
                self.defaults.set(self.logined, forKey: defaultsKeys.isLogined)
                 
            } else{
                print("failed")
            }
        }
    }
    
    
    func signOut(){
        self.logined = false
        self.defaults.set(self.logined, forKey: defaultsKeys.isLogined)

    }
}
