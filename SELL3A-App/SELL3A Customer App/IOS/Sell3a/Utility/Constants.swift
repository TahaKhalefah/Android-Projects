//
//  Constants.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 14/06/2021.
//

import Foundation
import SwiftUI

//MARK: - COLORS
let colorBlue:Color = Color("colorBlue")
let colorRed:Color = Color("colorRed")
let colorOvelayBlue:Color = Color("colorOverlayBlue")
let colorOvelayGray:Color = Color("colorOverlayGray")
let colorDarkGray:Color = Color("colorDarkGray")

let primaryBlue : Color = Color(red: 0.25, green: 0.75, blue: 1.0)
let primaryGray : Color = Color(red: 0.56, green: 0.6, blue: 0.69)
let shadowColor : Color = Color(red: 0.93, green: 0.94, blue: 0.95)
let neutralDark : Color = Color(red: 0.13, green: 0.2, blue: 0.39)


//MARK: - DATA


//MARK: - FUNCTION
func hexStringToUIColor (hex:String) -> UIColor {
    var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
    
    if (cString.hasPrefix("#")) {
        cString.remove(at: cString.startIndex)
    }
    
    if ((cString.count) != 6) {
        return UIColor.gray
    }
    
    var rgbValue:UInt64 = 0
    Scanner(string: cString).scanHexInt64(&rgbValue)
    
    return UIColor(
        red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
        green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
        blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
        alpha: CGFloat(1.0)
    )
}

struct defaultsKeys {
    static let firstNamekey = "firstNamekey"
    static let lastNamekey = "lastNamekey"
    static let fullNamekey = "fullNamekey"
    static let genderKey = "genderKey"
    static let phoneKey = "phoneKey"
    static let birthdayKey = "birthdayKey"
    static let addressKey = "addressKey"
    static let addressKey2 = "addressKey2"
    static let country = "countryKey"
    static let street = "streetKey"
    static let city = "cityKey"
    static let state = "stateKey"
    static let zip = "zipKey"
    static let profileImage = "profileImageKey"
    static let isLogined = "isLoginedKey"
    static let isLoginedGoogle = "isLoginedGoogleKey"

    static let loginName = "loginNameKey"
    static let loginEmail = "loginEmailKey"
    static let loginPhoto = "loginPhotoKey"

}

