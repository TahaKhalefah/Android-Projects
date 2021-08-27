//
//  SpecifficationView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct SpecifficationView: View {
    //MARK: - PROPERTY
    @State var company:String
    @State var brand:String
    @State var details:String
    
    var body: some View {
        VStack(alignment:.leading , spacing: 7){
            
            Text("specification")
                .font(.title2)
                .foregroundColor(colorDarkGray)
                .fontWeight(.bold)
                
            
            HStack{
                Text("company name: ")
                    .font(.subheadline)
                    .foregroundColor(colorOvelayGray)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                    .padding(.top , 4)
                
                Spacer()
                
                Text(company)
                    .font(.subheadline)
                    .foregroundColor(colorOvelayGray)
            }
            HStack{
                Text("brand: ")
                    .font(.subheadline)
                    .foregroundColor(colorOvelayGray)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                
                Spacer()
                
                Text(brand)
                    .font(.subheadline)
                    .foregroundColor(colorOvelayGray)
                
            }
            Text(details)
                .font(.subheadline)
                .padding(.top,2)
                .foregroundColor(colorOvelayGray)
                
        }
    }
}

struct SpecifficationView_Previews: PreviewProvider {
    static var previews: some View {
        SpecifficationView(company: "N/F", brand: "N/F", details: "N/F")
            .previewDevice( PreviewDevice (rawValue:"iPhone 12"))
    }
}
                                                                                                 
