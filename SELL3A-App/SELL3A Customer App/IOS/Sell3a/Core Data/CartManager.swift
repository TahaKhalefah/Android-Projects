//
//  CartManager.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 25/06/2021.
//

import Foundation
import CoreData

class CartManager{
    
    let container: NSPersistentContainer
    static let shared = CartManager()

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
    
    
    func  getAllCartItems () -> [CartModel] {
        
        let request: NSFetchRequest<CartModel> = CartModel.fetchRequest()
        do {
            return try viewContext.fetch(request)
            
        } catch {
            return []
        }
    }
    

    
    private func getCartById(id: NSManagedObjectID) -> CartModel? {
        
        do {
            return try viewContext.existingObject(with: id) as? CartModel
        } catch {
            return nil
        }
        
    }
    
    
    
    func deleteItem (model: CartModel) {
        viewContext.delete(model)
        save()
    }
    
    func deleteAll () {
        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "CartModel")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)

        do {
            try viewContext.execute(deleteRequest)
            try viewContext.save()
        } catch {
            print ("There was an error")
        }
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
