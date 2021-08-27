//
//  TitleAndPriceView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct TitleAndPriceView: View {
    //MARK: - PROPERTY
    @State var title:String
    @State var rating:Double
    @State var price:Int
    
    var body: some View {
        VStack(alignment:.leading , spacing:10){

            Text(title)
                .font(.title2)
                .foregroundColor(colorDarkGray)
                .fontWeight(.heavy)
                .padding(.vertical,3)
            
            CosmosRatingView(rating: $rating , enabled: false)
                
            
            Text("\(price) EGP")
                .foregroundColor(colorBlue)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
               
            
            
                
            
        }
    }
}

struct TitleAndPriceView_Previews: PreviewProvider {
    static var previews: some View {
        TitleAndPriceView(title: "not found", rating: 0, price: 0)
            .previewLayout(.sizeThatFits)
    }
}
