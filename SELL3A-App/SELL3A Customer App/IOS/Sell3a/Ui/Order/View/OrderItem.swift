//
//  OrderItem.swift
//  Sell3a
//
//  Created by Hager Samir on 26/06/2021.
//

import SwiftUI

struct OrderItem: View {
    @State var order : OrderResponse?
    var body: some View {
        
        VStack(alignment: .leading, spacing: 0, content: {
            if(order != nil){
            Text(order?.id ?? "OrderId").bold().font(.title2)
            Text("Order at Sell3a \(order!.orderState)").font(.title2).foregroundColor(.gray).padding(.bottom)
                        
            HStack{
                Text("Order State").foregroundColor(.gray)
                Spacer()
                Text(order!.orderState).foregroundColor(.gray)
            }
            
            HStack{
                Text("Item").foregroundColor(.gray)
                Spacer()
                Text("\(order!.itemIDS.count)").foregroundColor(.gray)
            }
            
            HStack{
                Text("Price").foregroundColor(.gray)
                Spacer()
                Text("\(order!.totalPrice) EGP").foregroundColor(.gray)
            }
            }
        }).padding().border(Color.gray)
        
    }
}

struct OrderItem_Previews: PreviewProvider {
    static var previews: some View {
        OrderItem().previewLayout(.sizeThatFits).padding()
    }
}
