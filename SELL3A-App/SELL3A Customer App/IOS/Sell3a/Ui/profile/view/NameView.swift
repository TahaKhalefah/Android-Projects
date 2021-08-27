//
//  NameView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct NameView: View {
    
    @State var selection: Int? = 0
    @State private var first_name = UserDefaults.standard.string(forKey: defaultsKeys.firstNamekey) ?? ""
    @State private var last_name =  UserDefaults.standard.string(forKey: defaultsKeys.lastNamekey) ?? ""
    
    let defaults = UserDefaults.standard
    
    var body: some View {
        NavigationView{
            
            VStack{
                Text("First Name " )
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.leading, 20)
                    .foregroundColor(colorDarkGray)
                    .font(.system(size: 20, weight: .heavy, design: .default))
                
                TextField("Mnem ", text: $first_name)
                    .foregroundColor(colorOvelayGray)
                    .padding()
                    .frame(width: 343, height: 48)
                    .font(.system(size: 20, weight: .medium, design: .default))
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(colorOvelayGray, lineWidth: 2)
                    )
                
                Text("Last Name " )
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.leading, 20)
                    .padding(.top, 20)
                    .foregroundColor(colorDarkGray)
                    .font(.system(size: 20, weight: .heavy, design: .default))
                
                TextField("Elmorshidy ", text: $last_name)
                    .foregroundColor(colorOvelayGray)
                    .padding()
                    .frame(width: 343, height: 48)
                    .font(.system(size: 20, weight: .medium, design: .default))
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(colorOvelayGray, lineWidth: 2)
                    )
                
                Spacer()
                
                NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selection) {
                    Button(action: {
                        
                        selection = 1 
                        let full_name =  first_name + " " + last_name
                        
                        defaults.set(self.first_name, forKey: defaultsKeys.firstNamekey)
                        defaults.set(self.last_name, forKey: defaultsKeys.lastNamekey)
                        defaults.set(full_name, forKey: defaultsKeys.fullNamekey)
                        
                        
                        if let fullName = defaults.string(forKey: defaultsKeys.fullNamekey) {
                            print(fullName)
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
                    
                }
            }
        }
//        .navigationBarHidden(true)
    }
}

struct NameView_Previews: PreviewProvider {
    static var previews: some View {
        NameView()
    }
}
