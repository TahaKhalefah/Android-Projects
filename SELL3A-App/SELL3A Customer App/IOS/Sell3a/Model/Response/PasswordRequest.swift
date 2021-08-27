//
//  PasswordRequest.swift
//  Sell3a
//
//  Created by Mnem on 26/06/2021.
//

import Foundation

struct PasswordRequest : Codable {
    let email: String
    let old_password: String
    let new_password: String
}

