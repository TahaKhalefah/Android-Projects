//
//  CategoryResponse.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import Foundation
struct CategoryResponse  : Codable , Hashable {
    let name : String
    let url : String

    enum CodingKeys: String, CodingKey {

        case name = "name"
        case url = "url"
    }

    init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name) ?? ""
        url = try values.decodeIfPresent(String.self, forKey: .url) ?? ""
    }

}
