//
//  Offer Screen.swift
//  Sell3a
//
//  Created by Hager Samir on 19/06/2021.
//

import SwiftUI

struct Offer_Screen: View {
    var body: some View {
        NavigationView{
            VStack{
                var cupone : String = self.generateCupone()
                Text("Use “\(cupone)” Cupon For Get 90%off").font(.title).foregroundColor(.white).padding().background(primaryBlue).cornerRadius(20)
                NavigationLink(destination: SaleProductsScreen(saleType: "flash sale")){
                    ZStack{
                        Image("flash_sale").cornerRadius(20)
                        HStack(){
                            Text("Flash Sale").foregroundColor(.white).font(.title).fontWeight(.bold)
                            
                        }
                    }.padding()
                }
                
                NavigationLink(destination: SaleProductsScreen(saleType: "mega sale")){
                    ZStack{
                        Image("mega_sale").cornerRadius(20)
                        HStack(){
                            Text("Mega Sale").foregroundColor(.white).font(.title).fontWeight(.bold)
                            
                        }
                    }.padding()
                }
                
            }.navigationBarHidden(true).padding()
        }
    }
    
    func generateCupone() -> String{
        let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
         return String((0..<5).map{ _ in letters.randomElement()! })
    }
}

struct Offer_Screen_Previews: PreviewProvider {
    static var previews: some View {
        Offer_Screen()
    }
}
