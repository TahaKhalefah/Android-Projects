//
//  OrderScreen.swift
//  Sell3a
//
//  Created by Hager Samir on 26/06/2021.
//

import SwiftUI

struct OrderScreen: View {
    @ObservedObject var viewModel = OrderViewModel()
    var body: some View {
        List(0..<viewModel.order.count, id : \.self) { item in
            OrderItem(order: viewModel.order[item])
        }.navigationBarTitle("Order" , displayMode: .inline).onAppear(perform: {
            viewModel.getOrders(id: "60cb7238b31e990015aabe72")
        })
    }
}

struct OrderScreen_Previews: PreviewProvider {
    static var previews: some View {
        OrderScreen()
    }
}
