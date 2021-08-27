//
//  ReviewModel.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 22/06/2021.
//

import Foundation
class ReviewViewModel : ObservableObject{

    @Published var isSuccess:Bool?
    @Published var reviewResponse:[ReviewResponse]?
    
    private let webService = ApiService.shared

    
    func addReview(request:AddReviewRequest){
        webService.addReview(request: request) { reviewResponse, error in
            if reviewResponse != nil{
                self.isSuccess = true
            }else{
                print(error!.localizedDescription)
            }
        }
    }
    
    func getReviews(itemId:Int , rating:Int = 0){
        webService.getReviews(itemId: itemId, rate: rating) { response, error in
            self.reviewResponse = nil
            if let error = error{
                print("error  \(error)")
            }else{
                self.reviewResponse = []
                self.reviewResponse = response!
             }
        }
    }
    
}
