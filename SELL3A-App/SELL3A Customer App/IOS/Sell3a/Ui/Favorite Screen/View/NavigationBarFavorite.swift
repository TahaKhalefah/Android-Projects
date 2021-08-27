//
//  NavigationBarFavorite.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 23/06/2021.
//

import SwiftUI

struct NavigationBarFavorite: View {
    //MARK: - PROPERTY
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    
    //MARK: - BODY
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
            
            Text("favorite")
                .font(.headline)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .lineLimit(1)
            
            
            Spacer()
        })
    }
}

struct NavigationBarFavorite_Previews: PreviewProvider {
    static var previews: some View {
        NavigationBarFavorite()
    }
}


