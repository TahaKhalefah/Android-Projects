//
//  ShipToScreen.swift
//  Sell3a
//
//  Created by Hager Samir on 24/06/2021.
//

import SwiftUI

struct ShipToScreen: View {
    var order : AddOrderRequest?
    @State var selection : Int? = nil
    var body: some View {
     
            VStack{
                NavigationBarShipTo()
                    .padding(.horizontal)
                       .padding(.bottom)
                       .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
                       .background(Color.white)
                       .shadow(radius: 3)
                
                VStack(alignment: .leading){
                     
                    Text("Egypt")
                        .foregroundColor(neutralDark)
                        .font(.system(size: 14, weight: .bold, design: .default))
                        .padding(.bottom , 12)
                        .padding(.leading , 16)
                    
                    Text("109, sidi bishr, Alexandria")
                        .foregroundColor(colorDarkGray)
                        .font(.system(size: 12, weight: .light, design: .default))
                        .padding(.bottom , 16)
                        .padding(.leading , 16)
                    
                    HStack{
                        Button(action: {
                            
                        }) {
                            Text("Edit")
                                .bold()
                                .font(.system(size: 14))
                                .foregroundColor(.white)
                                .frame(width: 77, height: 57)
                        }
                        .background(primaryBlue)
                        .overlay(
                            RoundedRectangle(cornerRadius: 8)
                                .stroke(primaryBlue, lineWidth: 5)
                        )
                        .padding(.leading , 16)
                        
                        Image("delete")
                            .padding(.leading , 28)
                            .frame(width: 24, height: 24, alignment: .center)
                        
                        Spacer()
                        
                    }
                   
                }
                .frame(width:343 , height: 240)
                .cornerRadius(10)
                .overlay(RoundedRectangle(cornerRadius: 10)
                            .stroke(primaryBlue, lineWidth: 2))
                .shadow(radius: 10)
            
                Spacer()
                
                NavigationLink(destination: OrderScreen(), tag: 2, selection: $selection ){
                    Button(action: {
                        selection = 2
                       
                    }) {
                        Text("Go To Order")
                            .bold()
                            .font(.system(size: 14))
                            .foregroundColor(.white)
                            .frame(width: 343, height: 57)
                    }
                    .background(primaryBlue)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(colorOvelayBlue, lineWidth: 5)
                    )
                }.padding(.bottom , 90)
                
            }.ignoresSafeArea()
            .navigationBarHidden(true)
        }
       
    }

//struct ShipToScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        ShipToScreen()
//    }
//}
