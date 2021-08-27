//
//  SaleListItems.swift
//  Sell3a
//
//  Created by Hager Samir on 24/06/2021.
//

import SwiftUI

struct SaleListItems: View {
    @State var products:[ProductResponse]
    @State var showDetails = false
    
    var body: some View {
        ScrollView(.horizontal){
            HStack{
                ForEach(0..<products.count/2, id: \.self) { i in
                    
                    NavigationLink(
                        destination: DetailsScreen(product:products[i] ),
                        isActive: $showDetails,
                        label: {
                            SaleItem(saleProduct: products[i])
                        })
                    
                    
                }
            }.padding()
        }
        
        ScrollView(.horizontal){
            HStack{
                ForEach(products.count/2..<products.count, id: \.self) { i in
                    NavigationLink(
                        destination: DetailsScreen(product:products[i] ),
                        isActive: $showDetails,
                        label: {
                            SaleItem(saleProduct: products[i])
                        })
                }
            }.padding()
        }    }
}

struct SaleListItems_Previews: PreviewProvider {
    static var previews: some View {
        SaleListItems(products: [])
    }
}
