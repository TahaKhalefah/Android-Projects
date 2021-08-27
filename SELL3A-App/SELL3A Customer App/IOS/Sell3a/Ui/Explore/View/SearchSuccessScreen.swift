//
//  SearchSuccessScreen.swift
//  Sell3a
//
//  Created by Taha Khalefah on 23/06/2021.
//

import SwiftUI

struct SearchSuccessScreen: View {
    var columns: [GridItem] =
        Array(repeating: .init(.flexible()), count: 2)
    @State var searchStr = ""
    @State var resultCount = 0
    var categoryName = "Default Name"
    var min = 0
    var max = 1000
    var title = ""
    var price = 0
    var page = 1
    var brand = ""
    var sale = 0
    @ObservedObject var searchObs = SearchSuccessViewModel()
    var body: some View {
        NavigationView{
            VStack{
                NavigationBarSearch()
                    .padding(.horizontal)
                       .padding(.bottom)
                       .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
                       .background(Color.white)
                       .shadow(radius: 3)
                
                SearchSuccessBar(text: $searchStr).padding()
                HStack{
                    Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                        HStack{
                            Text("Category")
                            Image(systemName: "arrow.down.circle")
                        }.foregroundColor(.blue)
                    })
                    
                    Spacer()
                    Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                        HStack{
                            Text("Price")
                            Image(systemName: "arrow.down.circle")
                        }.foregroundColor(.blue)
                    })
                    Spacer()
                    Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                        HStack{
                            Text("Brand")
                            Image(systemName: "arrow.down.circle")
                        }.foregroundColor(.blue)
                    })
                    Spacer()
                    Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                        HStack{
                            Text("Sale")
                            Image(systemName: "arrow.down.circle")
                        }.foregroundColor(.blue)
                    })
                    
                }.padding()
                HStack(){
                    Text(String(resultCount)).padding(.leading)
                    Text("Result")
                    Spacer()
                    Text(categoryName).padding(.trailing)
                }
                ScrollView{
                    
                    if(searchObs.recommendedProducts != nil){
                        LazyVGrid(columns: columns, spacing: 10, content: {
                            
                            ForEach(0..<searchObs.recommendedProducts!.count, id: \.self) { item in
                                NavigationLink(
                                    destination: DetailsScreen(product:searchObs.recommendedProducts![item]),
                                    label: {
                                SearchSuccessItem(saleProduct: searchObs.recommendedProducts![item])
                            }
                            )
                            }
                        }).padding(.horizontal)
                    }
                }
            }
            
            
        }.navigationBarHidden(true).ignoresSafeArea().onAppear(perform: {
            searchObs.getRecommendedProducts(categoryName:self.categoryName)
            //searchObs.filterProduct(min: min, max: max, price: price, category: categoryName, sale: sale, brand: brand, title: title, page: page)
        })
        
        
    }
}

struct SearchSuccessScreen_Previews: PreviewProvider {
    static var previews: some View {
        SearchSuccessScreen()
    }
}
