//
//  NavigationBarCart.swift
//  Sell3a
//
//  Created by Mahmoud Mousa on 26/06/2021.
//

import SwiftUI

struct NavigationBarCart: View {
    
    //MARK: - PROPERTY

    @State private var isAnimated:Bool = false
 
    
    var body: some View {
        HStack(alignment: .center, spacing: 5, content: {
            
    
            
            Spacer()
            
            Text("Cart")
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
            
        }).padding(.top,4)
    }
    
    
}

struct NavigationBarCart_Previews: PreviewProvider {
    static var previews: some View {
        NavigationBarCart()
    }
}


