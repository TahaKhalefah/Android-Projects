//
//  CategoryListItem.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct CategoryListItem: View {
    var name = ""
    var url = ""
    var body: some View {
        HStack{
            AnimatedImage(url: URL(string: url)).resizable().frame(width: 40, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            Text(name).padding(.horizontal)
            Spacer()
        }.padding()
    }
}

struct CategoryListItem_Previews: PreviewProvider {
    static var previews: some View {
        CategoryListItem().previewLayout(.sizeThatFits)
    }
}
