//
//  NoReviewsView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 19/06/2021.
//

import SwiftUI

struct NoReviewsView: View {
    var body: some View {
        HStack{
            Spacer()
        VStack(spacing:10){
            NavigationLink(
                destination: AddReviewView(),
                label: {
                    Text("+")
                        .font(.largeTitle)
                        .foregroundColor(.white)
                        .frame(width: 64, height: 64, alignment: .center)
                        .background(colorBlue)
                        .clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
                }).navigationBarHidden(true)
            
    
            
            Text("No Review Yet")
                .foregroundColor(colorDarkGray)
                .font(.headline)
                .fontWeight(.semibold)
            
            
        }
            Spacer()
    }
    }
}

struct NoReviewsView_Previews: PreviewProvider {
    static var previews: some View {
        NoReviewsView()
    }
}
