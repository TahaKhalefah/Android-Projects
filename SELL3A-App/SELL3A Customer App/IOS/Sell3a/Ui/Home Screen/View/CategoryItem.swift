//
//  CategoryItem.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct CategoryItem: View {
        var url = ""
        var body: some View {
            ZStack{
                Circle()
                    .fill(Color.white)
                    .frame(width: 60, height: 60).clipShape(Circle())
                                .shadow(radius: 5).overlay(Circle().stroke(Color.gray, lineWidth: 2))
                
                AnimatedImage(url: URL(string: url)).resizable().frame(width: 40, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            }
    }
}

struct CategoryItem_Previews: PreviewProvider {
    static var previews: some View {
        CategoryItem()
    }
}
