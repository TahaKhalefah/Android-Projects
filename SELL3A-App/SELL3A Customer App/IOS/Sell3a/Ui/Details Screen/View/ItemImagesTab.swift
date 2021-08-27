//
//  ItemImagesTab.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct ItemImagesTab: View {
    //MARK: - PROPERTY
    @State var image:String
    
    //MARK: - BODY
    var body: some View {
        AnimatedImage(url:URL(string: image))
            .resizable()
            .scaledToFill()
    }
}

//MARK: - PREVIEW
struct ItemImagesTab_Previews: PreviewProvider {
    static var previews: some View {
        ItemImagesTab(image:"https://i.stack.imgur.com/5ykYD.png")
            .previewLayout(.sizeThatFits)
    }
}
