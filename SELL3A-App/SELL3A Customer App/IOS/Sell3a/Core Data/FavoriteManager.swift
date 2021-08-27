//
//  CoreDataManager.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 22/06/2021.
//

import Foundation
import CoreData

class FavoriteManager{
    
    let container: NSPersistentContainer
    static let shared = FavoriteManager()

    private init() {
        container = NSPersistentContainer(name: "Sell3a")
        container.loadPersistentStores { (description, error) in
            if let error = error {
                print("Error Loading Core Data \(error)")
            }else{
                print("Core Data Loadded Success")
            }
        }
    }
    
    var viewContext: NSManagedObjectContext {
        return container.viewContext
    }
    
    
    func  getAllFavoritItems () -> [FavoriteModel] {
        
        let request: NSFetchRequest<FavoriteModel> = FavoriteModel.fetchRequest() 
        do {
            return try viewContext.fetch(request)
            
        } catch {
            return []
        }
    }
    

    
    private func getCartById(id: NSManagedObjectID) -> FavoriteModel? {
        
        do {
            return try viewContext.existingObject(with: id) as? FavoriteModel
        } catch {
            return nil
        }
        
    }
    
    
    
    func deleteItem (model: FavoriteModel) {
        viewContext.delete(model)
        save()
    }
    
  
    
    func save() {
        do {
            try viewContext.save()
            print("saving done ")
        } catch {
            viewContext.rollback()
            print("saving error "+error.localizedDescription)
        }
    }
    
  
    
}
