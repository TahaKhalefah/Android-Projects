//
//  SearchSuccessViewModel.swift
//  Sell3a
//
//  Created by Taha Khalefah on 23/06/2021.
//

import Foundation

class SearchSuccessViewModel: ObservableObject  {
    
    @Published var recommendedProducts:[ProductResponse]? = nil
    @Published var filterProducts:[ProductResponse]? = nil
    //@Published var catName : String = ""
    
    private let apiService = ApiService.shared
    

    

     func getRecommendedProducts(categoryName:String){
        apiService.getProductsByCategoryName(categoryName: categoryName) { response, error in
            if let error = error{
                print("error  \(error)")
            }else{
                self.recommendedProducts = response
             }
        }
    }
    
    func filterProduct(min:Int?=nil,max:Int?=nil,price:Int=0,category:String?=nil,sale:Int=0,brand:String?=nil,title:String?,page:Int = 1){
       apiService.filterProducts(min: min, max: max, price: price, category: category, sale: sale, brand: brand, title: title, page: page){ response, error in
           if let error = error{
               print("error  \(error)")
           }else{
               self.filterProducts = response
            }
       }
   }
}
