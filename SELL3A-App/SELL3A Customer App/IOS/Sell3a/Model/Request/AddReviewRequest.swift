//
//  AddReviewRequest.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 21/06/2021.
//

import Foundation
struct AddReviewRequest : Encodable {
    let userId:String
    let itemId:String
    let description:String
    let rating:Double
}
