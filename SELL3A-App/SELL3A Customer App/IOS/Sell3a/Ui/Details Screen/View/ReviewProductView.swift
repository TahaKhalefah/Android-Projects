//
//  ReviewProductView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ReviewProductView: View {
    
    //MARK: - Property
    @State var reviews:[ReviewResponse]=[]
    @State var itemId = -1
    var body: some View {
        VStack(alignment:.leading){
            HStack{
                Text("product reviews")
                    .font(.title2)
                    .foregroundColor(colorDarkGray)
                    .fontWeight(.bold)
                
                Spacer()
                
                NavigationLink(
                        destination:
                        ReviewScreen(itemId: itemId),
                        label: {
                            Text("see more")
                            .font(.headline)
                            .foregroundColor(colorBlue)
                        })
                
            }//HStack
            ItemReviewListView(review: reviews[0])
                .padding(.top,6)
        }//VStack
    }
}

struct ReviewProductView_Previews: PreviewProvider {
    static var previews: some View {
        ReviewProductView()
            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
    }
}
