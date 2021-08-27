//
//  OrderViewModel.swift
//  Sell3a
//
//  Created by Hager Samir on 26/06/2021.
//

import Foundation

class OrderViewModel: ObservableObject {
    
    @Published var order = [OrderResponse]()
    
    init(){
        getOrders(id: "")
    }

    
    func getOrders(id:String){
        ApiService.shared.getOrderById(userId: id,compilation: {(orderList : [OrderResponse]? , error) in
            if let erroe = error {
                print(erroe)
            }else{
                guard orderList != nil else {return}
                for item in orderList! {
                        self.order.append(item)
                    
                }
                print(self.order.count)
            }
        })
    }
}
