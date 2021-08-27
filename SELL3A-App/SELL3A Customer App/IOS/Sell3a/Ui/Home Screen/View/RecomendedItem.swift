//
//  RecomendedItem.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct RecomendedItem: View {
    @State private var rating: Double?
    @State var saleProduct :ProductResponse?

    var body: some View {
        
        VStack(alignment: .leading){
            AnimatedImage(url: URL(string:saleProduct?.image ?? "url")!).resizable().frame(width: 120, height: 120, alignment: .center).cornerRadius(20)
            
            Text(saleProduct?.title ?? " title").foregroundColor(.black).lineLimit(2).padding(5)
            
            RatingBar(rating:Int( (saleProduct?.rating ?? 0)/2) , max: 5).padding(5)
            
            
            if(saleProduct?.sale != nil){
                var newPrice = (saleProduct!.price) - ((saleProduct!.price) * Double(Float((((self.saleProduct!.sale!.amount / 100))))))
                
                Text("\(String(format: "%.2f", newPrice as! CVarArg))" ).foregroundColor(.blue).padding(5)
                
                HStack{
                    Text("\(String(format: "%.2f", saleProduct?.price as! CVarArg))" ).foregroundColor(.gray)
                    
                    Spacer()
                    Text("\(saleProduct?.sale?.amount ?? 0) %").foregroundColor(.red)
                }.padding(5)
            }else{
                Text("\(String(format: "%.2f", saleProduct?.price as! CVarArg))" ).foregroundColor(.blue).padding(5)
                
                HStack{
                    Text(" " ).foregroundColor(.gray)
                    
                    Spacer()
                    Text(" ").foregroundColor(.red)
                }.padding(5)
            }
            
            
        }.frame(width: 140, alignment: .top).padding().border(Color(.gray))
        
        
    }
}

struct RecomendedItem_Previews: PreviewProvider {
    static var previews: some View {
        RecomendedItem().previewLayout(.sizeThatFits)
    }
}
