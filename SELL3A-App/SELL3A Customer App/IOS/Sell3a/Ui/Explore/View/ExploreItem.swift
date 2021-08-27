//
//  ExploreItem.swift
//  Sell3a
//
//  Created by Taha Khalefah on 23/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI
struct ExploreItem: View {
    var url = ""
    var categoryName = ""
    var body: some View {
        VStack{
            ZStack{
                Circle()
                    .fill(Color.white)
                    .frame(width: 70, height: 70).clipShape(Circle())
                                .shadow(radius: 5).overlay(Circle().stroke(Color.gray, lineWidth: 1))
                
                AnimatedImage(url: URL(string: url)!).resizable().frame(width: 40, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            }
    
            Text(categoryName).lineLimit(1)
        }.padding()
 
}
}

struct ExploreItem_Previews: PreviewProvider {
    static var previews: some View {
        ExploreItem()
    }
}
