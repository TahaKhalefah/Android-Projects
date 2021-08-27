//
//  PasswordViewModel.swift
//  Sell3a
//
//  Created by Mnem on 24/06/2021.
//

import Foundation
import Alamofire

class PasswordViewModel: ObservableObject {
    
    @Published var data = [PasswordResponse]()
    @Published var isSuccess : Bool = false

    init(){
        ChangePasswordView()
    }
    
    
    func changePassword(request:PasswordRequest){
        ApiService.shared.changePassword(request: request) { PasswordResponse, error in
            if PasswordResponse != nil{
                self.isSuccess = true
            }else{
                print(error!.localizedDescription)
            }
        }
    }
    
}
