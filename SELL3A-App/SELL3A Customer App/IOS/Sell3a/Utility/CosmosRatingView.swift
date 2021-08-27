//
//  CosmosRatingView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI
import Cosmos

struct CosmosRatingView: UIViewRepresentable {
    @Binding var rating: Double
    @State var starSize:Double = 30
    @State var enabled = true
    @State var listener:((Double)->())?=nil

    func makeUIView(context: Context) -> CosmosView {
        CosmosView()
    }

    func updateUIView(_ uiView: CosmosView, context: Context) {
        uiView.rating = rating
    
        // Autoresize Cosmos view according to it intrinsic size
        uiView.setContentHuggingPriority(.defaultHigh, for: .vertical)
        uiView.setContentHuggingPriority(.defaultHigh, for: .horizontal)
      
        // Change Cosmos view settings here
        uiView.settings.starSize = starSize
        uiView.settings.totalStars = 5
        uiView.settings.emptyBorderColor = #colorLiteral(red: 0.2392156869, green: 0.6745098233, blue: 0.9686274529, alpha: 1)
        uiView.settings.updateOnTouch = enabled
        uiView.didFinishTouchingCosmos = listener
        
        //change star image
        /*
        // Set image for the filled star
        uiView.settings.filledImage = UIImage(named: "GoldStarFilled")
        
        // Set image for the empty star
        uiView.settings.emptyImage = UIImage(named: "GoldStarEmpty")
         */
    }
}

