//
//  AddReviewView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct AddReviewView: View {
    //MARK: - PROPERTY
    @ObservedObject var viewModel = ReviewViewModel()
    @State var rate:Double = 2.0
    @State var review:String = ""
    @State var itemId = -1
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    
    var body: some View {
        VStack(alignment:.leading){
        Text("Please write overall level of satisfaction with your shipping / Delivery Service")
            .font(.title2)
            .foregroundColor(colorDarkGray)
            .fontWeight(.heavy)
            .padding(.vertical,3)
            .padding(.bottom , 10)
        
            HStack(spacing:10){
                CosmosRatingView(rating: $rate,starSize: 35) { rating in
                    rate = rating
                }
                
                Text("(\(Int(rate))/5)")
            }
            .padding(.bottom , 10)
            
            Text("Write your review")
                .font(.title3)
                .fontWeight(.bold)
                .foregroundColor(colorDarkGray)
                
            
            TextEditor(text: $review)
                .padding(8)
                .frame(maxHeight:200)
                .foregroundColor(.secondary)
                .overlay(RoundedRectangle(cornerRadius: 10).stroke(colorOvelayBlue,lineWidth: 1.0))
                .font(.subheadline)
            
            Spacer()
            
            Button(action: {
                viewModel.addReview(request: AddReviewRequest(userId: "60cb7238b31e990015aabe72", itemId: "\(itemId)", description: review, rating: rate*2 ))
                   
                    
               
                
            }, label: {
                Text("Commit")
                    .padding()
                    
                    .font(.headline)
                    .foregroundColor(Color.white)
                    .frame(maxWidth:.infinity)
                    .background(colorBlue)
                    .frame(maxWidth: .infinity)
                    .cornerRadius(7)
            })
            
            if let _ = viewModel.isSuccess{
                Text("").onAppear {
                    print("item id---> \(itemId)")
                    self.presentationMode.wrappedValue.dismiss()
                }
               
            }
            
        }.padding()
    }
}

struct AddReviewView_Previews: PreviewProvider {
    static var previews: some View {
        AddReviewView()
    }
}
