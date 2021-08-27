//
//  CartItem.swift
//  Sell3a
//
//  Created by Hager Samir on 23/06/2021.
//

import SwiftUI
import SDWebImageSwiftUI

struct CartItem: View {
    @State var cartItem:CartModel? = nil
    @State var viewModel : CartViewModel
    
    
    var body: some View {
        HStack{
            if(cartItem?.title != nil){
                AnimatedImage(url: URL(string:cartItem?.image ?? "https://images.squarespace-cdn.com/content/v1/59d2bea58a02c78793a95114/1604517546555-J765GMQHYO74IFMZ2RWQ/ke17ZwdGBToddI8pDm48kFHDcct_fltA3KLnvRyGws9Zw-zPPgdn4jUwVcJE1ZvWQUxwkmyExglNqGp0IvTJZUJFbgE-7XRK3dMEBRBhUpz_xmy3d2yisJKMB5olewvGU1zvsFHW7wcxXg-c1peyE_IzyNgEDA6BxhwvJ1j-egw/iphone+12.png")!).resizable().frame(width: 90, height: 90, alignment: .center).border(colorOvelayBlue, width: /*@START_MENU_TOKEN@*/1/*@END_MENU_TOKEN@*/).cornerRadius(5)
                
                Spacer()
                
                VStack{
                    HStack{
                        Text(cartItem?.title ?? "Title").lineLimit(2).padding(5)
                        Spacer()
                        
                        
                        Button(action:
                                {
                                    viewModel.deleteItem(model: cartItem!)
                                }, label: {
                                    Image(systemName: "trash").font(.title).foregroundColor(.gray)
                                })
                    }.padding(5)
                    
                    
                    HStack{
                        Text("\(cartItem?.price ?? 0)").padding(5).foregroundColor(.blue)
                        
                        Spacer()
                        Button(action: {
                            cartItem?.quantity += 1
                        }, label: {
                            Image(systemName: "plus").font(.title3).foregroundColor(.gray)
                        })
                        
                        Text("\(cartItem?.quantity ?? 1)").padding(5)
                        Button(action: {
                            cartItem?.quantity -= 1
                        }, label: {
                            Image(systemName: "minus").font(.title3).foregroundColor(.gray)
                        })
                    }.padding(5)
                }
            }
        }.padding().border(colorOvelayBlue, width: 1).shadow(radius: 5)
    }
}

//struct CartItem_Previews: PreviewProvider {
//    static var previews: some View {
//        CartItem().previewLayout(.sizeThatFits)
//    }
//}
