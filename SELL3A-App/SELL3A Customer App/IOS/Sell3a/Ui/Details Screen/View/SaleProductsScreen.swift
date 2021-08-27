//
//  SaleProductsScreen.swift
//  Sell3a
//
//  Created by Hager Samir on 19/06/2021.
//

import SwiftUI

struct SaleProductsScreen: View {
    var saleType = ""
    @ObservedObject var obs = SaleViewModel()
    var columns: [GridItem] =
             Array(repeating: .init(.flexible()), count: 2)
    var body: some View {
        
        VStack(alignment: .leading, spacing: 0, content: {
            ZStack{
                Image("flash_sale").cornerRadius(20)
                HStack(){
                    Text(saleType).foregroundColor(.white).font(.title).fontWeight(.bold)
                    
                }
            }.padding()
            
            
            
            ScrollView{
            LazyVGrid(columns: columns, spacing: 15, content: {
                ForEach(0..<obs.data.count, id: \.self) { i in
                    if(obs.data[i].sale?.type == saleType){
                        RecomendedItem(saleProduct: obs.data[i])
                    }
                }
            }).padding(.horizontal).navigationBarTitle(saleType, displayMode: .inline)
            }.padding(.top)
        })
    }
}

struct SaleProductsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SaleProductsScreen()
    }
}
