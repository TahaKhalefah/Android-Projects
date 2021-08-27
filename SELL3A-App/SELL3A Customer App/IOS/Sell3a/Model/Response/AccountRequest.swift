//
//  AccountRequest.swift
//  Sell3a
//
//  Created by Mnem on 26/06/2021.
//

import Foundation

struct AccountRequest : Codable {
    let _id: String??
    let name: String??
    let email: String??
    let phoneNumber: String??
    let BirthDate: String??
    let Address: String??
    let Gender: String??
    let profileImage: String??
}
