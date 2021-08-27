//
//  DetailsViewModel.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 18/06/2021.
//

import Foundation
import Combine

class DetailsViewModel : ObservableObject {

    @Published var product :ProductResponse? = nil
    @Published var reviewResponse :[ReviewResponse]? = nil
    @Published var recommendedProducts:[ProductResponse]? = nil
   
    //MARK: - WEB SERVICE
    private let apiService = ApiService.shared

    func getReviews(itemId:Int , rating:Int = 0){
        apiService.getReviews(itemId: itemId, rate: rating) { response, error in
            if let error = error{
                print("error  \(error)")
            }else{
                self.reviewResponse = response
            }
        }
    }
    
    func getRecommendedProducts(categoryName:String){
        apiService.getProductsByCategoryName(categoryName: categoryName) { response, error in
            if let error = error{
                print("error  \(error)")
            }else{
                self.recommendedProducts = response
             }
        }
    }
    //MARK: - CORE DATA
    
    func addToFavorite(product : ProductResponse?) {
        
        if let product = product {
            let model = FavoriteModel(context: FavoriteManager.shared.viewContext)
            model.uID = UUID()
            model.id = Int32(product.id)
            model.title = product.title
            model.image = product.image
            model.price = product.price
            model.rating = product.rating ?? 0.0
            model.sale = Int16(product.sale?.amount ?? 0)
            
            FavoriteManager.shared.save()
        }
     }
    
    
    
    func addToCart(product : ProductResponse? , color : String , size : String) {
        
        if let product = product {
            let model = CartModel(context: CartManager.shared.viewContext)
            model.uID = UUID()
            model.id = Int16(product.id)
            model.title = product.title
            model.image = product.image
            model.price = product.price
            model.sale = Int16(product.sale?.amount ?? 0)
            model.image = product.image
            model.quantity = Int16(1)
            model.selctedColor = color
            model.selectedSize = size
            CartManager.shared.save()
            CartManager.shared.getAllCartItems()
        }
     }
    
    func getAllItems() {
       // let req = CoreDataManager.shared.getAllItems(Model: FavoriteModel.self)
    }
    
    
    
}
    


