//
//  AddToCartButtonView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct AddToCartButtonView: View {
    //MARK: - PROPERTY
    @State var viewModel:DetailsViewModel
    var product : ProductResponse?
    var selectedColor = ""
    var selectedSize = ""
    
    
    //MARK: - BODY
    var body: some View {
        Button(action: {
            viewModel.addToCart(product: product , color: selectedColor , size: selectedSize)
        }, label: {
            Text("Add To Cart")
                .padding()
                .font(.headline)
                .foregroundColor(Color.white)
                .frame(maxWidth:.infinity)
                
        
        })
        .background(colorBlue)
        .frame(maxWidth: .infinity)
        .cornerRadius(7)
    }
}

//struct AddToCartButtonView_Previews: PreviewProvider {
//    static var previews: some View {
//        AddToCartButtonView()
//    }
//}
