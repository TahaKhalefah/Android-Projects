//
//  TextAddressView.swift
//  Sell3a
//
//  Created by Mnem on 23/06/2021.
//

import SwiftUI

struct TextAddressView: View {
    
    var text : String
    
    var body: some View {
        Text(text )
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.leading, 20)
            .foregroundColor(neutralDark)
            .font(.system(size: 14, weight: .heavy, design: .default))
    }
}

struct TextAddressView_Previews: PreviewProvider {
    static var previews: some View {
        TextAddressView(text: "mnem")
    }
}
