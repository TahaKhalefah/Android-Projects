//
//  RegisterResponse.swift
//  Sell3a
//
//  Created by Mnem on 26/06/2021.
//

import Foundation

// MARK: - Welcome
struct RegisterResponse : Codable {
    let id, name, email: String
    let profileImage: String

    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case name, email, profileImage
    }
}

