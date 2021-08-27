//
//  ImagesTabView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ImagesTabView: View {
    @State var images:[String]
    var body: some View {
    
        TabView{
            ForEach(0..<images.count){ i in
                ItemImagesTab(image: images[i])
            }
        }.tabViewStyle(PageTabViewStyle(indexDisplayMode: .always))
    }
}

struct ImagesTabView_Previews: PreviewProvider {
    static var previews: some View {
        ImagesTabView(images: ["https://i.stack.imgur.com/5ykYD.png"])
            .background(Color.gray)
    }
}
