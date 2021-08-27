//
//  NavigationBarView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 14/06/2021.
//

import SwiftUI
struct NavigationBarView: View {
    
    //MARK: - PROPERTY
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    @State private var isAnimated:Bool = false
    @State var title:String
    @State var product:ProductResponse?
    @State private var isFavorite = false
    @State var viewModel:DetailsViewModel
    
    var body: some View {
        HStack(alignment: .center, spacing: 5, content: {
            
            Button(action: {
                self.presentationMode.wrappedValue.dismiss()
            }, label: {
                Image(systemName: "chevron.left")
                    .font(.title3)
                    .foregroundColor(colorDarkGray)
            })
            
            Spacer()
            
            Text(title)
                .font(.headline)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .lineLimit(1)
                .opacity(isAnimated ? 1 : 0)
                .offset(x: 0, y: isAnimated ? 0:-30)
                .onAppear(perform: {
                    withAnimation(.easeOut(duration: 0.8)){
                        isAnimated.toggle()
                    }
                })
            
            Spacer()
            
            
            Button(action: {
                isFavorite.toggle()
                
                viewModel.addToFavorite(product: product)
                //viewModel.getAllItems()
                
            }, label: {
                Image(systemName: isFavorite ? "heart.fill" : "heart")
                    .padding(.trailing, 5)
            })
            
        }).padding(.top,4)
    }
}

//struct NavigationBarView_Previews: PreviewProvider {
//    static var previews: some View {
//        NavigationBarView(title: "mahmoud")
//            .previewLayout(.sizeThatFits)
//
//    }
//}
