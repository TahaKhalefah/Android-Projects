//
//  HomeViewModel.swift
//  Sell3a
//
//  Created by Hager Samir on 18/06/2021.
//

import Foundation


import Alamofire

class HomeViewModel: ObservableObject {
    let allProductUrl = "https://souqitigraduationproj.herokuapp.com/api/products/getall"
    let saleUrl = "https://souqitigraduationproj.herokuapp.com/api/products/getsales"
    @Published var data = [ProductResponse]()
    @Published var allData = [ProductResponse]()
    init() {
        
        getSaleItems()
        getRecommendedItems()
    
    }
    

    

    private func getSaleItems(){
        ApiService.shared.getProduct(url: self.saleUrl, complition: {(ProductList : [ProductResponse]? , error) in
            if let erroe = error {
                print(erroe)
            }else{
                guard let category = ProductList else {return}
                self.data = ProductList!
                print(self.data.count)
            }
        })
    }
    
    private func getRecommendedItems(){
        ApiService.shared.getProduct(url: self.allProductUrl, complition: {(ProductList : [ProductResponse]? , error) in
            if let erroe = error {
                print(erroe)
            }else{
                guard let category = ProductList else {return}
                self.allData = ProductList!
                print(self.allData.count)
            }
        })
    }
}
