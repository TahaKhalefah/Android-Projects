//
//  ExploreScreen.swift
//  Sell3a
//
//  Created by Taha Khalefah on 23/06/2021.
//

import SwiftUI

struct ExploreScreen: View {
    var columns: [GridItem] =
        Array(repeating: .init(.flexible()), count: 3)
        @ObservedObject var obs = CategoryViewModel()
    @State var searchStr = ""
        var body: some View {
            NavigationView{
                VStack{
                    SearchBar(text: $searchStr)
                   
                    ScrollView{
                
                        LazyVGrid(columns: columns, spacing: 10, content: {
                            ForEach(0..<obs.data.count, id: \.self) { item in
                                NavigationLink(
                                    destination: SearchSuccessScreen(categoryName: obs.data[item].name),
                                    label: {
                                        ExploreItem(url: obs.data[item].url, categoryName: obs.data[item].name)
                                    })
                              
                            }
                        }).padding(.horizontal)
                    }.padding(.top)
                    
                }
                     
            }.ignoresSafeArea().navigationBarHidden(true)
                  
        }
    
}

struct ExploreScreen_Previews: PreviewProvider {
    static var previews: some View {
        ExploreScreen()
    }
}
