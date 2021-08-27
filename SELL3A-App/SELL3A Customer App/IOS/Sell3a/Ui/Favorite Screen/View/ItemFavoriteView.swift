//
//  ItemFavoriteView.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 23/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct ItemFavoriteView: View {
    //MARK: - PROPERTY
    @State var product : FavoriteModel
    @State var viewModel : FavoriteViewModel
    
    //MARK: - BODY
    var body: some View {
        VStack(alignment:.leading , spacing:10){
           
            
            VStack(alignment:.leading , spacing:10){
               
                
                AnimatedImage(url: URL(string: product.image ?? ""))
                    .resizable()
                    .scaledToFit()
                    
                
                    
                
                Text(product.title ?? "" + "\n")
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                
                CosmosRatingView(rating: .constant((product.rating )/2) ,enabled: false)
            
                let newPrice:String = product.sale != nil ? "\(product.price - (product.price * Double(Double(product.sale)/100.0))) EGP" : "\(product.price) EGP"
                
                Text(newPrice)
                    .fontWeight(.semibold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorBlue)
            }//VStack
            
            
            HStack(spacing:10){
                let oldPrice:String = product.sale != nil ? "\(product.price) EGP" : ""
                        
                let amount:String = product.sale != nil ? "\(product.sale)%":""
                
                Text(oldPrice)
                    .font(.caption)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorDarkGray)
                
                Text(amount)
                    .font(.caption)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(colorRed)
                Spacer()
                Button(action: {
                    viewModel.deleteItem(model: product)
                }, label: {
                    Image(systemName: "trash")
                })
                
            }
            
            
        }//VStack
        .padding(10)
        .frame(width: 180)
        .background(Color.white)
    }
    }


//struct ItemFavoriteView_Previews: PreviewProvider {
//    static var previews: some View {
//        ItemFavoriteView()
//    }
//}
