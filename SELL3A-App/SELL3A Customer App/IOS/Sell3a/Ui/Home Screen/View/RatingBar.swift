//
//  RatingBar.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct RatingBar: View {
    @State var rating: Int?
    var max: Int = 5
    var body: some View {
        HStack {
               ForEach(1..<(max + 1), id: \.self) { index in
                   Image(systemName: self.starType(index: index))
                       .foregroundColor(Color.orange)
//                      .onTapGesture {
//                        self.rating = Double(index)
//                        
//                      }
               }
           }
    }
    
    private func starType(index: Int) -> String {
           
           if let rating = self.rating {
            return index <= Int(rating) ? "star.fill" : "star"
           } else {
               return "star"
           }
           
       }
}

struct RatingBar_Previews: PreviewProvider {
    static var previews: some View {
        RatingBar(rating: 3)
    }
}
