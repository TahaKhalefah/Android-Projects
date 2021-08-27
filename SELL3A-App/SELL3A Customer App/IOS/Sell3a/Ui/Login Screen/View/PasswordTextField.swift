//
//  PasswordTextField.swift
//  Sell3a
//
//  Created by Mnem on 25/06/2021.
//

import SwiftUI

struct PasswordTextField: View {
    @Binding var text : String
    var placeholder : String
    var img : String
    
    var body: some View {
        HStack{
            Image(systemName:img)
                .frame(width: 20, height: 9.7, alignment: .center)
                .foregroundColor(primaryGray)
            
            SecureField(placeholder, text: $text)
              .foregroundColor(primaryGray)
                .autocapitalization(.none)
        }.padding()
            .frame(width: 343, height: 48)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(primaryGray, lineWidth: 2)
        )
    }
}

struct PasswordTextField_Previews: PreviewProvider {
    static var previews: some View {
        PasswordTextField(text: .constant(""), placeholder: "password" ,img: "lock")
    }
}
