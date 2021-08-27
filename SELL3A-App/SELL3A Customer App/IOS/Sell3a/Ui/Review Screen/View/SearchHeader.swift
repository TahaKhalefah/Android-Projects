//
//  SearchHeader.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct SearchHeader: View {
    
    //MARK: - PROPERTY
    @State var minimumRate = 0
    @Binding var selectedRate:Int
    var body: some View {
        HStack{
            
            Text("All Review")
                .padding(10)
                .background(colorOvelayBlue)
                .foregroundColor(Color.blue)
                .font(.subheadline)
                .cornerRadius(/*@START_MENU_TOKEN@*/3.0/*@END_MENU_TOKEN@*/)
            
            
            ScrollView(.horizontal, showsIndicators: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, content: {
                HStack{
                ForEach(1..<6){i in
                    if minimumRate == i {
                        ItemSearchHeader(number: i , rate: $minimumRate).background(colorOvelayBlue).onAppear {
                            selectedRate = i

                        }
                        
                        
                    }else{
                        ItemSearchHeader(number: i , rate: $minimumRate)
                    }
                    
                }
                }
            })
        }.padding(8)
    }
}

struct SearchHeader_Previews: PreviewProvider {
    static var previews: some View {
        SearchHeader(selectedRate: .constant(0))
    }
}
