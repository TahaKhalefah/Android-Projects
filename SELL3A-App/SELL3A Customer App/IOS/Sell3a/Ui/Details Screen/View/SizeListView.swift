//
//  SizeListView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct SizeListView: View {
    //MARK: - PROPERTY
    
    let data:[String]
    @State var selectedSize:String = ""
    @Binding var isSelectSize:Bool
    
    
    var body: some View {
        VStack(alignment:.leading){

            
            Text("select size")
                .font(.title2)
                .foregroundColor(colorDarkGray)
                .fontWeight(.bold)
            
            
            ScrollView(.horizontal, showsIndicators: false, content: {
                HStack{
                    ForEach(0..<data.count){i in
                        if data[i] == selectedSize{
                            
                            ItemSizeList(size: data[i],selectedSize: $selectedSize)
                                .background(colorOvelayGray)
                                .overlay(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/.stroke(colorDarkGray, lineWidth: 3))
                                .clipShape(Circle())
                                .padding(.trailing , 10).onAppear(perform: {
                                    isSelectSize = true
                                })
                               
                            
                        }else{
                        ItemSizeList(size: data[i],selectedSize: $selectedSize)
                            .background(Color.white)
                            .overlay(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/.stroke(colorDarkGray, lineWidth: 3))
                            .clipShape(Circle())
                            .padding(.trailing , 10)
                        }
                    }
                }//HStack
                .padding(.vertical,1)
                .padding(.horizontal,2)
            })//Scroll
            
        }//VStack
    }
}

//struct SizeListView_Previews: PreviewProvider {
//    static var previews: some View {
//        SizeListView(data: ["m","m"])
//            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
//    }
//}
