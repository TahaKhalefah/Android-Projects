//
//  FavoriteViewModel.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 23/06/2021.
//

import Foundation

class FavoriteViewModel: ObservableObject {
    
   
    @Published var favoriteItems: [FavoriteModel] = []
       
        func getAllItems() {
            favoriteItems = FavoriteManager.shared.getAllFavoritItems()
       }
    
    func deleteItem(model: FavoriteModel )  {
        FavoriteManager.shared.deleteItem(model: model)
        favoriteItems = []
        getAllItems()
    }
    
 
    
}
