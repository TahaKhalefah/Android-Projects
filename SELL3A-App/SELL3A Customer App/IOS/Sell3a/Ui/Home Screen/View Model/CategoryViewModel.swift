//
//  CategoryObserve.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import Foundation

import Alamofire

class CategoryViewModel: ObservableObject {
    let categoryUrl = "https://souqitigraduationproj.herokuapp.com/api/products/getcategories"
    @Published var data = [CategoryResponse]()
    init() {
        
        ApiService.shared.getProduct(url: self.categoryUrl, complition: {(categoryList : [CategoryResponse]? , error) in
            if let erroe = error {
                print(erroe)
            }else{
                guard let category = categoryList else {return}
                self.data = categoryList!
                print(self.data)
            }
        })
        
        
        
        
        
        
//        AF.request().responseData {(data) in
//            let json = try! JSON(data : data.data!)
//            for i in json{
//                self.data.append(CategoryResponse(id: "", name: i.1["name"].stringValue, url: i.1["url"].stringValue))
//            }
//            print(self.data)
//        }
    }
}
