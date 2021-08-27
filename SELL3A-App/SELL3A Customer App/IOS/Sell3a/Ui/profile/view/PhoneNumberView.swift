//
//  PhoneNumberView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct PhoneNumberView: View {
    
    @State private var phone_number =  UserDefaults.standard.string(forKey: defaultsKeys.phoneKey) ?? ""
    @State private var selectionPhone: Int? = 0
    let defaults = UserDefaults.standard
    
    var body: some View {
        NavigationView{
            VStack{
                Text("Phone Number " )
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.leading, 20)
                    .foregroundColor(colorDarkGray)
                    .font(.system(size: 20, weight: .heavy, design: .default))
                    .padding(.top,20)
                
                CustomTextField(text: $phone_number, placeholder: "(307) 555-0133", img: "iphone")
                
                Spacer()
                NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selectionPhone) {
                    Button(action: {
                        selectionPhone = 1
                        defaults.set(self.phone_number, forKey: defaultsKeys.phoneKey)
                        
                        if let phoneNum = defaults.string(forKey: defaultsKeys.phoneKey) {
                            print(phoneNum)
                        }
                    }) {
                        Text("Save")
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
//        .navigationBarHidden(true)
    }
}
struct PhoneNumberView_Previews: PreviewProvider {
    static var previews: some View {
        PhoneNumberView()
    }
}
