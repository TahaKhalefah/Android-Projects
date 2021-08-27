//
//  FavoriteScreen.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 23/06/2021.
//

import SwiftUI

struct FavoriteScreen: View {
    //MARK: - PROPERTY
    @ObservedObject var viewModel = FavoriteViewModel()

    var layouts = [GridItem(.flexible()) , GridItem(.flexible())]
    //MARK: - BODY
        var body: some View {
            VStack{
                NavigationBarFavorite()
                    .padding(.horizontal)
                       .padding(.bottom)
                       .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
                       .background(Color.white)
                       .shadow(radius: 3) .onAppear(perform: {
                        viewModel.getAllItems()
                })
                   
                
                ScrollView{
                    LazyVGrid(columns: layouts){
                        ForEach(0..<viewModel.favoriteItems.count , id: \.self) { i in
                            ItemFavoriteView(product: viewModel.favoriteItems[i] , viewModel: viewModel)
                                .shadow(radius: 5)
                        }
                }.padding(.top)
                
                   
                
                }
            
            }.navigationBarHidden(true)
            .ignoresSafeArea()
    }
            
}

struct FavoriteScreen_Previews: PreviewProvider {
    static var previews: some View {
        FavoriteScreen()
    }
}



//                        HStack{
//                            ScrollView{
//                            ForEach(0..<viewModel.favoriteItems.count){i in
//                                ItemFavoriteView(product: viewModel.favoriteItems[i])
//                            }
//                        }
//                    }
