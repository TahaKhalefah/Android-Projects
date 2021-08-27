//
//  AddressTextField.swift
//  Sell3a
//
//  Created by Mnem on 23/06/2021.
//

import SwiftUI

struct AddressTextField: View {
    
    @Binding var text : String
    var placeholder : String
    
    var body: some View {
        TextField(placeholder, text: $text)
            .foregroundColor(primaryGray)
            .padding()
            .frame(width: 343, height: 48)
            .font(.system(size: 12, weight: .bold, design: .default))
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(primaryGray, lineWidth: 1)
            )
    }
}

struct AddressTextField_Previews: PreviewProvider {
    static var previews: some View {
        AddressTextField(text: .constant(""), placeholder: "")
    }
}
