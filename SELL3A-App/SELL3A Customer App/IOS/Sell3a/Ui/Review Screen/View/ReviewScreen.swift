//
//  ReviewScreen.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct ReviewScreen: View {
    //MARK: - PROPERTY
    var i = 1
    @State var itemId = -1
    @StateObject private var viewModel = ReviewViewModel()
    @State private var minimumRate = -1
    
    //MARK: - BODY
    var body: some View {
        VStack(alignment:.leading){
            NavigationBarReview() .padding(.horizontal)
                .padding(.bottom)
                .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
                .background(Color.white)
                .shadow(radius: 3).onAppear(perform: {viewModel.getReviews(itemId: itemId)})
            
            SearchHeader(selectedRate: $minimumRate)
            if let _ = viewModel.reviewResponse{
                ReviewsListView(reviews:viewModel.reviewResponse!)
            }
            
            if(minimumRate != -1){
                Text("").onAppear {
                    print("minimum--- \(minimumRate)")
                    viewModel.getReviews(itemId: itemId, rating: minimumRate)
                }
            }

            

            Spacer()
            AddReviewButton(itemId : itemId)
                .padding()
                .padding(.bottom , 20)
        }//VStack
        .ignoresSafeArea()
        .navigationBarHidden(true)
    }
}

//MARK: - PREVIEW
struct ReviewScreen_Previews: PreviewProvider {
    static var previews: some View {
        ReviewScreen()
        
    }
}
