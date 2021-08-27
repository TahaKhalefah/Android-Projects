//
//  GenderView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct GenderView: View {
    @State private var gender =  UserDefaults.standard.string(forKey: defaultsKeys.genderKey) ?? "Male"
    @State private var selectionGender: Int? = 0
    let defaults = UserDefaults.standard
    
    var body: some View {
        VStack {
        
            
            Text("Choose Gender" )
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20)
                .foregroundColor(colorDarkGray)
                .font(.system(size: 20, weight: .heavy, design: .default))
                .padding(.top,20)
            
            Picker(selection: $gender, label: Text("Gender")) {
                Text("Male").tag("Male")
                Text("Female").tag("Female")
            }
            
            Spacer()
            
            NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selectionGender) {
                Button(action: {
                    selectionGender = 1
                    defaults.set(self.gender, forKey: defaultsKeys.genderKey)
                    
                    if let gender = defaults.string(forKey: defaultsKeys.genderKey) {
                        print(gender)
                    }
                    print(gender)
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
        .navigationBarHidden(true)
    }
}

struct GenderView_Previews: PreviewProvider {
    static var previews: some View {
        GenderView()
    }
}
