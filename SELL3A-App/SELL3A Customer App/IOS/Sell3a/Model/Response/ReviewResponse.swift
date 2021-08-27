
//  ReviewResponse.swift
//  Sel3a
//  Created by Mahmoud Mousa on 19/06/2021.


import Foundation

// MARK: - ReviewResponseElement
struct ReviewResponse: Codable {
    let id: String
    let userID: String
    let itemID:String
    let reviewResponseDescription: String
    let rating: Double
    let v: Int

    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case userID = "userId"
        case itemID = "itemId"
        case reviewResponseDescription = "description"
        case rating
        case v = "__v"
    }
}
