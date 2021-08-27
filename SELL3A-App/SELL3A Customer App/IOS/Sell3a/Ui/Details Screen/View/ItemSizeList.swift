//
//  ItemSizeList.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ItemSizeList: View {
    @State var size:String
   
    @Binding var selectedSize:String
    var body: some View {


        Button(action: {
            
            selectedSize = size
        }, label: {
            Text(size)
                .frame(width: 64, height: 64, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .font(.headline)
                .foregroundColor(colorBlue)  
        })
            
    }
}

//struct ItemSizeList_Previews: PreviewProvider {
//    static var previews: some View {
//        ItemSizeList(size: "XL")
//    }
//}
