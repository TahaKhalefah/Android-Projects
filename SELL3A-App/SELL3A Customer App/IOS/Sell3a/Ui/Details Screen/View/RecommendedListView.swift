//
//  RecommendedListView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct RecommendedListView: View {
    //MARK: - PROPERTY
    @State var products:[ProductResponse]
    
    //MARK: - BODY
    var body: some View {
        VStack(alignment:.leading){
            Text("you might also like")
                .font(.title2)
                .foregroundColor(colorDarkGray)
                .fontWeight(.bold)
            
            ScrollView(.horizontal){
                HStack{
                    ForEach(0..<products.count){i in
                        NavigationLink(
                            destination: DetailsScreen(product:products[i]),
                            label: {
                                ItemRecommendedList(product: products[i])
                                    .shadow(color: /*@START_MENU_TOKEN@*/.black/*@END_MENU_TOKEN@*/.opacity(0.4), radius:2, x: 0.0, y: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/)
                                    .padding(5)
                            })
                    }
                }//VStack
            }//Scroll
            
        }
    }
}

struct RecommendedListView_Previews: PreviewProvider {
    static var previews: some View {
        RecommendedListView(products: [])
            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
    }
}
