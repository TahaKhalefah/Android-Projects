//
//  SaleViewModel.swift
//  Sell3a
//
//  Created by Hager Samir on 19/06/2021.
//

import Foundation
import Alamofire

class SaleViewModel: ObservableObject {
    
    @Published var data = [ProductResponse]()
    
    init(){
        getSaleItems()
    }

    
     func getSaleItems(){
        ApiService.shared.getSaleProduct(compilation: {(ProductList : [ProductResponse]? , error) in
            if let erroe = error {
                print(erroe)
            }else{
                guard ProductList != nil else {return}
                for item in ProductList! {
                        self.data.append(item)
                    
                }
                print(self.data.count)
            }
        })
    }
    
   
    
}
