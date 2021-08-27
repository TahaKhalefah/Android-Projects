//
//  NavigationBarShipTo.swift
//  Sell3a
//
//  Created by Hager Samir on 24/06/2021.
//

import SwiftUI

struct NavigationBarShipTo: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    var body: some View {
        HStack(alignment: .center, spacing: 5, content: {
            
            Button(action: {
                self.presentationMode.wrappedValue.dismiss()
            }, label: {
                Image(systemName: "chevron.left")
                    .font(.title3)
                    .foregroundColor(colorDarkGray)
            })
            
            Spacer()
            
            Text("Ship To")
                .font(.headline)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .lineLimit(1)
            
            
            Spacer()
        })    }
}

struct NavigationBarShipTo_Previews: PreviewProvider {
    static var previews: some View {
        NavigationBarShipTo()
    }
}
