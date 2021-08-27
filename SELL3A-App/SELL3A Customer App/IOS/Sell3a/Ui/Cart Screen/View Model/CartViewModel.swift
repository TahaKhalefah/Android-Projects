//
//  CartViewModel.swift
//  Sell3a
//
//  Created by Hager Samir on 24/06/2021.
//

import Foundation

class CartViewModel: ObservableObject {
    

    private let webService = ApiService.shared
    @Published var isSuccess:Bool?
    @Published var cartItems:[CartModel]=[]

    func getCartItems() {
        cartItems = []
        cartItems = CartManager.shared.getAllCartItems()
    }
    //API
    
    func addOrder(request:AddOrderRequest){
        print("orderRequest \(request)")
        webService.addOrder(request: request) { orderResponse, error in
            if orderResponse != nil{
                self.isSuccess = true
            }else{
                print(error!.localizedDescription)
            }
        }
    }
    
    func deleteAllItems(){
        CartManager.shared.deleteAll()
    }
    
    func deleteItem(model: CartModel )  {
        CartManager.shared.deleteItem(model: model)
        cartItems = []
        getCartItems()
    }
    
    
    //coreData
        func getAllItems() {
            //cartItems = CoreDataManager.shared.getAllCartItems()
       }
    


    
}
