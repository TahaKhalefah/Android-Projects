//
//  ItemColorList.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ItemColorList: View {
    //MARK: - PROPERTY
    @State var color:String
    @Binding var selectedColor:String
    
    var body: some View {
        Button(action: {
            selectedColor = color
        }, label: {
            Text("")
                .frame(width: 64, height: 64, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .background(Color.white)
                .overlay(Circle().shadow(radius: 10))
                .clipShape(Circle())
                .shadow(radius: 2)
                .foregroundColor(Color(hexStringToUIColor(hex: color)))
                .padding(.trailing , 10)
        })
        
            
    }
}

//struct ItemColorList_Previews: PreviewProvider {
//    static var previews: some View {
//        ItemColorList(color: "#ffffff")
//    }
//}
