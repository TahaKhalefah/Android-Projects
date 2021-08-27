//
//  DetailsScreen.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 14/06/2021.
//

import SwiftUI

struct DetailsScreen: View {
    //MARK: - PROPERTY
    @State var product:ProductResponse? = nil
    @ObservedObject var viewModel = DetailsViewModel()
    @State var isSelectColor = false
    @State var isSelectSize = false
    var selectedColor = ""
    var selectedSize = ""
    
    //MARK: - BODY
    var body: some View {
       
        VStack(spacing:0){
            
            if let product = product{
                NavigationBarView(title: product.title , product:product , viewModel: viewModel)
                    .onAppear(perform: {
                        viewModel.getReviews(itemId: product.id)
                        viewModel.getRecommendedProducts(categoryName: product.category.name)
                        //fillCartData()
                    })
                    .padding(.horizontal)
                    .padding(.bottom)
                    .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
                    .background(Color.white)
                    .shadow(radius: 3)
                
                ScrollView{
                    VStack(alignment:.leading){
                        
                        ImagesTabView(images:[product.image])
                            .frame( height: 250)
                        
                        TitleAndPriceView(title: product.title, rating: (product.rating ?? 2)/2, price:Int( product.price))
                            .padding(.horizontal,6)
                        
                        if let sizeList = product.size{
                           
                            SizeListView(data:sizeList, isSelectSize: $isSelectSize)
                            .padding(.horizontal,6)
                            .padding(.top,10)
                        }
                        
                        if let colorList = product.color{
                            ColorsListView(colors: colorList, isSelectColor: $isSelectColor)
                            .padding(.horizontal , 6)
                            .padding(.top , 10)
                        }
                        
                        SpecifficationView(company: product.companyName, brand: product.brand, details: product.description)
                            .padding(.horizontal , 6)
                            .padding(.top , 10)
                        
                        if let reviews = viewModel.reviewResponse{
                            if !reviews.isEmpty{
                                ReviewProductView(reviews: reviews , itemId: product.id)
                                    .padding(.horizontal , 6)
                                    .padding(.top , 10)
                            }else{
                                NoReviewsView()
                                    .padding(.top , 10)
                            }
                        }
                        
                        if let recommended = viewModel.recommendedProducts{
                            RecommendedListView(products: recommended)
                            .padding(.horizontal , 6)
                            .padding(.top , 10)
                        }
                        AddToCartButtonView(viewModel: viewModel , product: product , selectedColor: ColorsListView(colors: product.color ?? [], isSelectColor: $isSelectColor).selectedColor , selectedSize: SizeListView(data: product.size ?? [], isSelectSize: $isSelectSize).selectedSize)
                            .padding(.horizontal , 6)
                            .padding(.top , 10)
                            .frame(width:.infinity)
                        Spacer()
                
                        
                        
                    }//VStack
                    
                }//Scroll
                .padding(.bottom , UIApplication.shared.windows.first?.safeAreaInsets.bottom)

            }
            
        }//VStack
        .padding(.bottom , 50)
        .ignoresSafeArea()
        .navigationBarHidden(true)
    
    }
    
//    func fillCartData() {
//        viewModel.cartModel.id = Int16(product!.id)
//        viewModel.cartModel.title = product!.title
//        viewModel.cartModel.image = product!.image
//        viewModel.cartModel.price = product!.price
//        viewModel.cartModel.sale = Int16(product!.sale?.amount ?? 0)
//    }
}

struct DetailsScreen_Previews: PreviewProvider {
    static var previews: some View {
        DetailsScreen()
        
    }
}
