//
//  CategoryList.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI

struct CategoryList: View {
    @ObservedObject var obs = CategoryViewModel()
    var body: some View {
        List(0..<obs.data.count , id : \.self) { item in
            NavigationLink(
                destination: SearchSuccessScreen(categoryName: obs.data[item].name),
                label: {
            CategoryListItem(name: obs.data[item].name, url: obs.data[item].url)
                })
            
               }.navigationBarTitle("Category" , displayMode: .inline)
        
    }
}

struct CategoryList_Previews: PreviewProvider {
    static var previews: some View {
        CategoryList()
    }
}
