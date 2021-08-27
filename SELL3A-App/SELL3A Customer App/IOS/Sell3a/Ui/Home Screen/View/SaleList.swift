//
//  SaleList.swift
//  Sell3a
//
//  Created by Hager Samir on 18/06/2021.
//

import SwiftUI

struct SaleList: View {
    @State var productList = [ProductResponse]()
    var body: some View {
        VStack{
            // sale items
            HStack{
                Text("Sale Items")
                Spacer()
                Text("More Sale").foregroundColor(.blue)
            }.padding()
            
            ScrollView(.horizontal){
                HStack{
                    ForEach(0..<productList.count/2, id: \.self) { i in
                        NavigationLink(
                            destination: DetailsScreen(product:productList[i]),
                            label: {
                                SaleItem(saleProduct: productList[i])
                            })
                        
                    }
                }.padding()
            }
            
            ScrollView(.horizontal){
                HStack{
                    ForEach(productList.count/2..<productList.count, id: \.self) { i in
                        SaleItem(saleProduct: productList[i])
                    }
                }.padding()
            }
        }
    }
}

struct SaleList_Previews: PreviewProvider {
    static var previews: some View {
        SaleList()
    }
}
