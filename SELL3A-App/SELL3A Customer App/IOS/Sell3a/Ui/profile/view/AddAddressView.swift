//
//  AddAddressView.swift
//  Sell3a
//
//  Created by Mnem on 23/06/2021.
//

import SwiftUI

struct AddAddressView: View {
    @State private var country = UserDefaults.standard.string(forKey: defaultsKeys.country) ?? ""
    @State private var street = UserDefaults.standard.string(forKey: defaultsKeys.street) ?? ""
    @State private var city = UserDefaults.standard.string(forKey: defaultsKeys.city) ?? ""
    @State private var state = UserDefaults.standard.string(forKey: defaultsKeys.state) ?? ""
    @State private var zip =  UserDefaults.standard.string(forKey: defaultsKeys.zip) ?? ""
    @State private var selectionPhone: Int? = 0
    let defaults = UserDefaults.standard
    
    @State private var address : String = ""
    @State private var address2 : String = ""
    
    @State var selection: Int? = 0
    
    var body: some View {
        
        NavigationView{
            VStack{
                
                VStack{
                    
                    TextAddressView(text: "Country or Region")
                    
                    AddressTextField(text: $country, placeholder: "")
                    
                    TextAddressView(text: "Street Address")
                    
                    AddressTextField(text: $street, placeholder: "")
                    
                    TextAddressView(text: "City")
                    
                    AddressTextField(text: $city, placeholder: "")
                }
                
                VStack{
                    TextAddressView(text: "State/Province/Region")
                    
                    AddressTextField(text: $state, placeholder: "")
                    
                    TextAddressView(text: "Zip Code")
                    
                    AddressTextField(text: $zip, placeholder: "")
                    
                    Spacer()
                  
                    NavigationLink(destination: AddressView(), tag: 1, selection: $selection) {
                        Button(action: {
                            address = street + ", " + city
                            address2 = country + ", " + city
                            
                            defaults.set(self.country, forKey: defaultsKeys.country)
                            defaults.set(self.state, forKey: defaultsKeys.state)
                            defaults.set(self.city, forKey: defaultsKeys.city)
                            defaults.set(self.street, forKey: defaultsKeys.street)
                            defaults.set(self.zip, forKey: defaultsKeys.zip)
                            defaults.set(address, forKey: defaultsKeys.addressKey)
                            defaults.set(address2, forKey: defaultsKeys.addressKey2)
                            
                             selection = 1
                        }) {
                            Text("Add Address")
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
                        .padding(.bottom)

                    }
                }
            }
        }
        .navigationBarHidden(true)
    }
}

struct AddAddressView_Previews: PreviewProvider {
    static var previews: some View {
        AddAddressView()
    }
}

