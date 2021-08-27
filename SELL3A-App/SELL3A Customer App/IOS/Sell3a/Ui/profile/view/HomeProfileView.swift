//
//  HomeProfileView.swift
//  Sell3a
//
//  Created by Mnem on 22/06/2021.
//

import SwiftUI
import Firebase
import GoogleSignIn


struct HomeProfileView: View {
    let defaults = UserDefaults.standard
    @State private var name = UserDefaults.standard.string(forKey: defaultsKeys.loginName) ?? "User Name"
    @State private var gender = UserDefaults.standard.string(forKey: defaultsKeys.genderKey) ?? ""
    @State private var birthday = UserDefaults.standard.string(forKey: defaultsKeys.birthdayKey) ?? ""
    @State private var address = UserDefaults.standard.string(forKey: defaultsKeys.addressKey2) ?? ""
    @State private var phone = UserDefaults.standard.string(forKey: defaultsKeys.phoneKey) ?? ""
    @State private var profileImage = UserDefaults.standard.string(forKey: defaultsKeys.profileImage) ?? "profile"
    @State private var email = UserDefaults.standard.string(forKey: defaultsKeys.loginEmail) ?? "email"

    
    @State var selectionName: Int? = 0
    @State var selectionGender: Int? = 0
    @State var selectionBirthday: Int? = 0
    @State var selectionAddress: Int? = 0
    @State var selectionPassword: Int? = 0
    @State var selectionPhone: Int? = 0
    @State var selectionLogout: Int? = 0

    @StateObject private var modifyAccountVM = ModifyAccountViewModel()
    @StateObject private var loginVM = LoginViewModel()

    
    var body: some View {
        NavigationView{
            VStack{
                
//                NavigationBarView(title: "Profile" )
//                .padding(.horizontal)
//                .padding(.bottom)
//                .padding(.top, UIApplication.shared.windows.first?.safeAreaInsets.top)
//                .background(Color.white)
//                .shadow(radius: 3)

                HStack{
                    Image(profileImage)
                        .resizable()
                        .frame(width:72 , height: 72)
                        .cornerRadius(10)
                        .overlay(RoundedRectangle(cornerRadius: 10)
                                    .stroke(primaryBlue, lineWidth: 1))
                        .shadow(radius: 10)
                        .padding(.leading, 16)
                    
                    VStack(alignment: .leading){
                        NavigationLink(destination: NameView(), tag: 1, selection: $selectionName) {
                            Text("Mnem Elmorshidy")
                                .padding(.leading, 0)
                                .padding(.bottom , 2)
                                .foregroundColor(neutralDark)
                                .font(.system(size: 14, weight: .heavy, design: .default))
                        }
                        Text("mnem2021@gmail.com")
                            .padding(.leading, 0)
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                    }
                    Spacer()
                }
                .padding(.bottom , 32)
                
                NavigationLink(destination: GenderView(), tag: 1, selection: $selectionGender) {
                    HStack{
                        Image("Gender")
                            .resizable()
                            .frame(width: 24, height: 24)
                        
                        Text("Gender")
                            .foregroundColor(neutralDark)
                            .font(.system(size: 12, weight: .bold, design: .default))
                        
                        Spacer()
                        
                        Text(gender)
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                        
                        Image("Vector")
                            .resizable()
                            .frame(width: 6, height: 12)
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
                
                NavigationLink(destination: BirthdayView(), tag: 1, selection: $selectionBirthday) {
                    HStack{
                        Image("Date")
                            .resizable()
                            .frame(width: 24, height: 24)
                        
                        Text("Birthday")
                            .foregroundColor(neutralDark)
                            .font(.system(size: 12, weight: .bold, design: .default))
                        
                        Spacer()
                        
                        Text(birthday)
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                        
                        Image("Vector")
                            .resizable()
                            .frame(width: 6, height: 12)
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
                
                NavigationLink(destination: AddressView(), tag: 1, selection: $selectionAddress) {
                    HStack{
                        Image("Location")
                            .resizable()
                            .frame(width: 24, height: 24)
                        
                        Text("Address")
                            .foregroundColor(neutralDark)
                            .font(.system(size: 12, weight: .bold, design: .default))
                        
                        Spacer()
                        
                        Text(address)
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                        
                        Image("Vector")
                            .resizable()
                            .frame(width: 6, height: 12)
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
                
                
                NavigationLink(destination: PhoneNumberView(), tag: 1, selection: $selectionPhone) {
                    HStack{
                        Image("Phone")
                            .resizable()
                            .frame(width: 24, height: 24)
                        
                        Text("Phone Number")
                            .foregroundColor(neutralDark)
                            .font(.system(size: 12, weight: .bold, design: .default))
                        
                        Spacer()
                        
                        Text(phone)
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                        
                        Image("Vector")
                            .resizable()
                            .frame(width: 6, height: 12)
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
                
                NavigationLink(destination: ChangePasswordView(), tag: 1, selection: $selectionPassword) {
                    HStack{
                        Image("Password")
                            .resizable()
                            .frame(width: 24, height: 24)
                        
                        Text("Change Password")
                            .foregroundColor(neutralDark)
                            .font(.system(size: 12, weight: .bold, design: .default))
                        
                        Spacer()
                        Text("********")
                            .foregroundColor(primaryGray)
                            .font(.system(size: 12, weight: .medium, design: .default))
                        
                        Image("Vector")
                            .resizable()
                            .frame(width: 6, height: 12)
                    }
                    .padding(.horizontal)
                    .padding(.bottom)
                }
                
                
//                NavigationLink(destination: Login(), tag: 1, selection: $selectionLogout) {
//                    Button(action: {
////                        loginVM.signOut()
//                        let firebaseAuth = Auth.auth()
//                    do {
//                      try firebaseAuth.signOut()
//                    } catch let signOutError as NSError {
//                      print ("Error signing out: %@", signOutError)
//                    }
//                      
//                        selectionLogout = 1
//                    }) {
//                        HStack{
//                        Image("logout")
//                            .renderingMode(.original)
//                            .resizable()
//                            .padding()
//                        
//                        Text("LogOut")
//                            .bold()
//                            .font(.system(size: 14))
//                            .foregroundColor(primaryGray)
//                            .frame(width: 243, height: 57)
//                        
//                        Spacer()
//                    }
//                    }
//                    .padding()
//                    .frame(width: .infinity, height: 30)
//      
//                }
//                
                
                Spacer()
                
                Button(action: {
//                    modifyAccountVM.modifyAccount(request: AccountRequest(_id: "hager", name: name, email: "hagersamir47@gmail.com", phoneNumber: phone, BirthDate: birthday, Address: address, Gender: gender, profileImage: profileImage))
                }) {
                    Text("Update")
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




struct HomeProfileView_Previews: PreviewProvider {
    static var previews: some View {
        HomeProfileView()
    }
}
