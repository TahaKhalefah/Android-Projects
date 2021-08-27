//
//  CustomTextField.swift
//  Sell3a
//
//  Created by Mnem on 15/06/2021.
//

import SwiftUI

struct CustomTextField: View {
    
    @Binding var text : String
    var placeholder : String
    var img : String
    
    var body: some View {
        HStack{
            Image(systemName:img)
                .frame(width: 20, height: 9.7, alignment: .center)
                .foregroundColor(colorOvelayGray)
            
            TextField(placeholder, text: $text)
              .foregroundColor(colorOvelayGray)
                .font(.system(size: 20, weight: .medium, design: .default))

        }.padding()
            .frame(width: 343, height: 48)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(colorOvelayGray, lineWidth: 2)
        )
    }
}

struct CustomTextField_Previews: PreviewProvider {
    static var previews: some View {
        CustomTextField(text: .constant(""), placeholder: "password" ,img: "lock")
    }
}

