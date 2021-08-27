//
//  ProductResponse.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 18/06/2021.
//

import Foundation
struct ProductResponse: Codable {
       let color, size: [String]?
       let brand: String
       let category: Category
       let companyName:String
       let description: String
       let id: Int
       let image: String
       let price: Double
       let quantity: Int
       let sale: Sale?
       let title: String
       let v: Int?
       let rating: Double?
   }

   // MARK: - Category
   struct Category: Codable {
       let name: String
       let url: String
   }

   // MARK: - Sale
   struct Sale: Codable {
       let amount: Int
       let duration: String
       let image: [String]?
       let type: String
   }
