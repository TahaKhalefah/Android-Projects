//
//  RegisterViewModel.swift
//  Sell3a
//
//  Created by Mnem on 25/06/2021.
//

import Foundation

class RigesterViewModel: ObservableObject {
    
    var webServices = ApiService.shared
    var name : String = ""
    var email: String = ""
    var password: String = ""
    
    
    func Register(){
        
        webServices.callingRegisterAPI(register: RegisterModel.init(name: name , email: email, password: password)) { isTrue in
            
            if isTrue {
                print("succes")
                
            } else{
                print("failed")
            }
            
        }
    }
}
