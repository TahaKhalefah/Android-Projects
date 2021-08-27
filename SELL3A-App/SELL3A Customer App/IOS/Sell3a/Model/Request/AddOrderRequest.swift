//
//  AddOrderResponse.swift
//  Sell3a
//
//  Created by Hager Samir on 24/06/2021.
//

import Foundation


struct AddOrderRequest : Encodable {
    var itemIds : [ItemIds]
    var orderState : String
    var importCharge : Int
    var Address : String
    var orderCode : String
    var orderDate : String
    var totalPrice : Int
    var userId : String



}



struct ItemIds : Encodable {
    var color : String
    var companyName : String
    var count : Int
    var id : String
    var size : String
 

}
