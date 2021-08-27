//
//  OrderResponse.swift
//  Sell3a
//
//  Created by Hager Samir on 26/06/2021.
//

import Foundation

// MARK: - ReviewResponse
struct OrderResponse: Codable {
    let itemIDS: [ItemID]
    let orderState: String
    let importCharge: Int
    let id, address, orderCode, orderDate: String
    let totalPrice: Int
    let userID: String
    let v: Int

    enum CodingKeys: String, CodingKey {
        case itemIDS = "itemIds"
        case orderState, importCharge
        case id = "_id"
        case address = "Address"
        case orderCode, orderDate, totalPrice
        case userID = "userId"
        case v = "__v"
    }
}

// MARK: - ItemID
struct ItemID: Codable {
    let color, companyName: String
    let count: Int
    let id, size: String
}
