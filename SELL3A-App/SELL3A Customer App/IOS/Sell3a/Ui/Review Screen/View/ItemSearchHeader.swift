//
//  ItemSearchHeader.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct ItemSearchHeader: View {
    //MARK: - PROPERTY
    @State var number:Int = 1
    @Binding var rate:Int
    
    //MARK: - BODY
    var body: some View {
        Button(action: {
            rate = number
        }, label: {
            HStack(spacing:5){
                
                Text("\(number)")
                    .foregroundColor(colorOvelayGray)
                    .font(.headline)
                

                Image(systemName: "star.fill")
                    .foregroundColor(Color.yellow)
                
            }.padding(10)
            .overlay(RoundedRectangle(cornerRadius: 5).stroke(colorOvelayGray, lineWidth: 1.0))
    
            
            
            
            
            
        })
    }
}

////MARK: - PREVIEW
//struct ItemSearchHeader_Previews: PreviewProvider {
//    static var previews: some View {
//        ItemSearchHeader()
//        
//    }
//}
