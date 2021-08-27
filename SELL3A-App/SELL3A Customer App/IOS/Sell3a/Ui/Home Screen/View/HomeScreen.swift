//
//  HomeScreen.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct HomeScreen: View {
    //MARK: - PROPERTY
    var saleItems = [ProductResponse]()
    var columns: [GridItem] =
        Array(repeating: .init(.flexible()), count: 2)
    @State private var searchText : String = ""
    @State private var FavoritSelection: String? = "favorit"
    @State var selection: Int? = nil
    @State var showDetails = false
    @ObservedObject var obs = CategoryViewModel()
    @ObservedObject var homeObs = HomeViewModel()
    
    
    var body: some View {
        NavigationView{
            VStack(spacing: 0){
                // searchBar
                SearchBar(text: $searchText )
                    .padding(.bottom)
                ScrollView{
                    //ViewPager
                    ImageSlider().padding(.top, -20)
                    
                    HStack{
                        Text("Category")
                            .font(.subheadline)
                            .fontWeight(.bold)
                        Spacer()
                        
                        NavigationLink(
                            destination:
                                CategoryList()
                            , tag: 1, selection: $selection) {
                            Button(action: {
                                self.selection = 1
                            }) {
                                Text("More Category")
                                
                            }
                                
                            }}.padding(.horizontal)
                        .padding(.top)
                    
                    // category list
                    ScrollView(.horizontal){
                        HStack{
                            ForEach(0..<obs.data.count, id : \.self) { item in
                                NavigationLink(destination: SearchSuccessScreen(categoryName: obs.data[item].name)){
                                    CategoryItem(url: obs.data[item].url  ).padding(.leading , 15)
                                }
                            }
                        }.padding(5)
                    }
                    
                    // sale items
                    HStack{
                        Text("Sale Items")
                            .font(.subheadline)
                            .fontWeight(.bold)
                        Spacer()
                        NavigationLink(
                            destination:
                                Offer_Screen()
                            , tag: 2, selection: $selection) {
                            Button(action: {
                                self.selection = 2
                            }) {
                                Text("More Sale")
                            }
                                
                            }
                    }.padding()
                    
                    ScrollView(.horizontal){
                        HStack{
                            ForEach(0..<homeObs.data.count/2, id: \.self) { i in
                                
                                NavigationLink(
                                    destination: DetailsScreen(product:homeObs.data[i] ),
                                    label: {
                                        SaleItem(saleProduct: homeObs.data[i] ) .shadow(color: /*@START_MENU_TOKEN@*/.black/*@END_MENU_TOKEN@*/.opacity(0.4), radius:2, x: 0.0, y: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/)
                                            .padding(5)
                                    })
                            }
                        }.padding(.horizontal)
                    }
                    
                    ScrollView(.horizontal){
                        HStack{
                            ForEach(homeObs.data.count/2..<homeObs.data.count, id: \.self) { i in
                                NavigationLink(
                                    destination: DetailsScreen(product:homeObs.data[i] ),
                                    isActive: $showDetails,
                                    label: {
                                        SaleItem(saleProduct: homeObs.data[i] ) .shadow(color: /*@START_MENU_TOKEN@*/.black/*@END_MENU_TOKEN@*/.opacity(0.4), radius:2, x: 0.0, y: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/)
                                            .padding(5)
                                    })
                            }
                        }.padding(.horizontal)
                    }
                    
                    //Recomended
                    ZStack{
                        Image("recomended")
                        VStack(alignment: .leading){
                            Text("Recomended Product").foregroundColor(.white).font(.title).fontWeight(.bold)
                            
                            Text("We recommend the best for you").foregroundColor(.white).font(.subheadline)
                        }
                    }.padding(.bottom)
                    if(homeObs.allData != nil){
                        
                        LazyVGrid(columns: columns, spacing: 10, content: {
                            ForEach(0..<homeObs.allData.count, id: \.self) { item in
                                
                                NavigationLink(
                                    destination: DetailsScreen(product:homeObs.allData[item]),
                                    label: {
                                        SaleItem(displayRating:true, saleProduct: homeObs.allData[item])
                                            .shadow(color: /*@START_MENU_TOKEN@*/.black/*@END_MENU_TOKEN@*/.opacity(0.4), radius:2, x: 0.0, y: /*@START_MENU_TOKEN@*/0.0/*@END_MENU_TOKEN@*/)
                                    })

                               
                                 
                                
                            }
                        }).padding(.horizontal)
                    }
                    Spacer()
                }
            }
            .navigationBarHidden(true)
            
        }
    }
    
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen().previewLayout(.sizeThatFits).ignoresSafeArea()
    }
}
