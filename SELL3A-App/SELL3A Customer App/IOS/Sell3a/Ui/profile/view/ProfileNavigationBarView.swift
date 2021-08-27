//
//  ProfileNavigationBarView.swift
//  Sell3a
//
//  Created by Mnem on 25/06/2021.
//

import SwiftUI

struct ProfileNavigationBarView: View {
    
    //MARK: - PROPERTY
    @State var isAnimated:Bool = false
    @State var title:String
    
    var body: some View {
        NavigationView{
        HStack(alignment: .center, spacing: 5, content: {
            
            Button(action: {
                
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
            
        
            
        }).padding(.top,4)
    }
    }
    
}


struct ProfileNavigationBarView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileNavigationBarView(title: "profile")
    }
}
