//
//  SearchSuccessBar.swift
//  Sell3a
//
//  Created by Taha Khalefah on 25/06/2021.
//

import SwiftUI

struct SearchSuccessBar: View {
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
            
            
            //                TextField("Search", text: $text)
            //                        .padding(10)
            //                        .background(Color.gray.opacity(0.2))
            
            Spacer()
            
            NavigationLink(destination: FavoriteScreen()){Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                      Image(systemName:"square.and.pencil").font(.title3).foregroundColor(.gray)
                })
             
                
            })
            }
            
            Spacer()
            
            NavigationLink(destination: FavoriteScreen()){Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                
                Image("notification").font(.title).foregroundColor(.gray)
            })
            }
            
            //            Image("notification").frame(width: 40, height: 40, alignment: .center)
            
            
        }.shadow(radius: 5)
        
    }
}
struct SearchSuccessBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchSuccessBar(text: .constant("")).previewLayout(.sizeThatFits).padding()
    }
}
