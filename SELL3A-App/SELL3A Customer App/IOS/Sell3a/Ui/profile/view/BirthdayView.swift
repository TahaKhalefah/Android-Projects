//
//  BirthdayView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI

struct BirthdayView: View {
    
    let defaults = UserDefaults.standard
    @State private var selection: Int? = 0
    
    let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        return formatter
    }()
    
    @State private var birthDate = Date()
    //    @State private var birthday = UserDefaults.standard.string(forKey: defaultsKeys.birthdayKey) ?? String(" \(birthDate)")
    
    // Convert Date to String
    //    dateFormatter.string(from: birthDate)
    
    var body: some View {
        NavigationView{
            VStack {
                
                Spacer()
                
                DatePicker(selection: $birthDate, in: ...Date(), displayedComponents: .date) {
                    
                    Text("Your Birthday" )
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.leading, 20)
                        .foregroundColor(neutralDark)
                        .font(.system(size: 18, weight: .bold, design: .default))
                    
                }
                
                Spacer()
                NavigationLink(destination: HomeProfileView(), tag: 1, selection: $selection) {
                    
                    Button(action: {
                        selection = 1
                        //                defaults.set(self.phone_number, forKey: defaultsKeys.phoneKey)
                        //
                        //                if let phoneNum = defaults.string(forKey: defaultsKeys.phoneKey) {
                        //                    print(phoneNum)
                        //                }
                        print(birthDate)
                        
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
        .navigationBarHidden(true)
    }
}

struct BirthdayView_Previews: PreviewProvider {
    static var previews: some View {
        BirthdayView()
    }
}
