//
//  SearchBar.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct SearchBar: View {
    @Binding var text : String
    @State private var isEditing = false
    var body: some View {
        
        HStack{
            TextField("Search ...", text: $text)
                .padding(7)
                .padding(.horizontal, 25)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .padding(.horizontal, 10).overlay(
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.leading, 15)
                        
                        if isEditing {
                            Button(action: {
                                self.text = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding(.trailing, 8)
                            }
                        }
                    })
            
            
//                            TextField("Search", text: $text)
//                                    .padding(10)
//                                    .background(Color.gray.opacity(0.2))
            
            Spacer()
            
            NavigationLink(
                destination: FavoriteScreen(),
                label: {
                    Image(systemName:"heart").font(.title3).foregroundColor(.gray)
                })

            
            Spacer()
            
//            NavigationLink(destination: FavoriteScreen()){Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
//
//                Image("notification").font(.title).foregroundColor(.gray)
//            })
//            }
            
            //            Image("notification").frame(width: 40, height: 40, alignment: .center)
            
            
        }.shadow(radius: 5)
        
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(text: .constant("")).previewLayout(.sizeThatFits).padding()
    }
}
