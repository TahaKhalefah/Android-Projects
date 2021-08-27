//
//  SaleItem.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct SaleItem: View {
    //MARK: - PROPERTY
    @State var displayRating:Bool = false
    @State var saleProduct :ProductResponse
    
    //MARK: - VIEW
    var body: some View {
        VStack(alignment:.leading , spacing:10){
            
            
            VStack(alignment:.leading , spacing:10){
                
                
                AnimatedImage(url: URL(string: saleProduct.image))
                    .resizable()
                    .scaledToFit()
                Text(saleProduct.title+"\n")
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                if displayRating{
                    CosmosRatingView(rating: .constant((saleProduct.rating ?? 2)/2) ,starSize:22, enabled: false)
                }
                let newPrice:String = saleProduct.sale != nil ? "\(saleProduct.price - (saleProduct.price * Double(Double(saleProduct.sale!.amount)/100.0))) EGP" : "\(saleProduct.price) EGP"
                
                Text(newPrice)
                    .fontWeight(.semibold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorBlue)
            }//VStack
            
            
            HStack(spacing:10){
                let oldPrice:String = saleProduct.sale != nil ? "\(saleProduct.price) EGP" : ""
                
                let amount:String = saleProduct.sale != nil ? "\(saleProduct.sale!.amount)%":""
                
                Text(oldPrice)
                    .font(.caption)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorDarkGray)
                
                Text(amount)
                    .font(.caption)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorRed)
            }
            
            
        }//VStack
        .padding(10)
        .frame(width: 170 , height: 310)
        .background(Color.white)
    }
}


//struct ItemRecommendedList_Previews: PreviewProvider {
//    static var previews: some View {
//        ItemRecommendedList()
//            .previewLayout(.sizeThatFits)
//    }
//}
