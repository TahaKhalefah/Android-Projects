//
//  ImageSlider.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct ImageSlider: View {
    @ObservedObject var obs = HomeViewModel()
    var body: some View {
        TabView{
            ForEach(0..<obs.data.count , id: \.self) { item in
                ViewPager(url: obs.data[item].image ?? "url").padding(.horizontal , 16 )
            }
        }.tabViewStyle(PageTabViewStyle())
        .indexViewStyle(PageIndexViewStyle(backgroundDisplayMode: .always)).frame( height: 250, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)    }
}

struct ImageSlider_Previews: PreviewProvider {
    static var previews: some View {
        ImageSlider().previewLayout(.sizeThatFits).background(Color.gray)
    }
}
