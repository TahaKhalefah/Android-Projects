//
//  AddressView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct AddressView: View {
    
    var country : String = UserDefaults.standard.string(forKey: defaultsKeys.country) ?? ""
    var address : String = UserDefaults.standard.string(forKey: defaultsKeys.addressKey) ?? ""
    @State var selectionaddress: Int? = 0
    @State var selectionHome: Int? = 0

    var body: some View {
        NavigationView{
            VStack{
                
                VStack(alignment: .leading){
                    
                    Text(country)
                        .foregroundColor(neutralDark)
                        .font(.system(size: 14, weight: .bold, design: .default))
                        .padding(.bottom , 12)
                        .padding(.leading , 16)
                    
                    Text(address)
                        .foregroundColor(colorDarkGray)
                        .font(.system(size: 12, weight: .light, design: .default))
                        .padding(.bottom , 16)
                        .padding(.leading , 16)
                    
                    HStack{
                        
                        NavigationLink(destination: AddAddressView(), tag: 1, selection: $selectionaddress) {
                            
                            Button(action: {
                                selectionaddress = 1
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
                        }
                        
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

                NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selectionHome) {

                Button(action: {
                    selectionHome = 1
                }) {
                    Text("Done")
                        .bold()
                        .font(.system(size: 14))
                        .foregroundColor(.white)
                        .frame(width: 343, height: 57)
                }
                .background(primaryBlue)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(primaryBlue, lineWidth: 5)
                )
                }
                
            }
            .padding()
            
        }
        //        .navigationBarHidden(true)
    }
}

struct AddressView_Previews: PreviewProvider {
    static var previews: some View {
        AddressView()
    }
}
