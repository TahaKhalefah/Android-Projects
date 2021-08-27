//
//  ViewPager.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct ViewPager: View {
    var url = ""
    var body: some View {
        AnimatedImage(url: URL(string: url)!).resizable().scaledToFit().cornerRadius(20)
    }
}

struct ViewPager_Previews: PreviewProvider {
    static var previews: some View {
        ViewPager().previewLayout(.sizeThatFits).padding()
    }
}
