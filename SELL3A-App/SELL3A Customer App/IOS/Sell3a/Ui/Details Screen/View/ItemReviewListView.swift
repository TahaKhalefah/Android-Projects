//
//  ItemReviewListView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ItemReviewListView: View {
    //MARK: - PROPERTY
    @State var review:ReviewResponse? = nil
    var body: some View {
        VStack(alignment: .leading, spacing: 16, content: {
            HStack{
                Image("profilePic")
                    .resizable()
                    .scaledToFill()
                    .frame(width: 64, height: 64, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    .clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
                    .shadow(radius: 10)
                
                VStack(alignment:.leading , spacing: 5){
                    Text("Mahmoud")
                        .foregroundColor(colorDarkGray)
                        .font(.headline)
                        .fontWeight(.semibold)
                    
                    CosmosRatingView(rating: .constant(review!.rating/2),enabled: false)
        
                }//VStack
                .padding(.horizontal,4)
            }//HStack
            
            Text(review!.reviewResponseDescription)
                .foregroundColor(colorDarkGray)
                .padding(.horizontal)
            
        })//VStack
    }
}

struct ItemReviewListView_Previews: PreviewProvider {
    static var previews: some View {
        ItemReviewListView()
    }
}
