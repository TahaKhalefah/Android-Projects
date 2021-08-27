//
//  AddReviewButton.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct AddReviewButton: View {
    //MARK: - PROPERTY
    @State var itemId = -1
    
    
    //MARK: - BODY
    var body: some View {
        NavigationLink(
            destination: AddReviewView(itemId:itemId),
            label: {
                Text("Add Review")
                    .padding()
                    
                    .font(.headline)
                    .foregroundColor(Color.white)
                    .frame(maxWidth:.infinity)
                    .background(colorBlue)
                    .frame(maxWidth: .infinity)
                    .cornerRadius(7)
            })
    }
}

struct AddReviewButton_Previews: PreviewProvider {
    static var previews: some View {
        AddReviewButton()
    }
}
