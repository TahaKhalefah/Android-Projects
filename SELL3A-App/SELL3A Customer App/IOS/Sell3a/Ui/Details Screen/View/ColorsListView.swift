//
//  ColorsListView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 15/06/2021.
//

import SwiftUI

struct ColorsListView: View {
    //MARK: - PROPERTY
    @State var colors:[String]
    @State var selectedColor:String=""
    @Binding var isSelectColor:Bool

    //MARK: - BODY
    var body: some View {
        VStack(alignment:.leading){
            
            Text("select color")
                .font(.title2)
                .foregroundColor(colorDarkGray)
                .fontWeight(.bold)
            
            
            ScrollView(.horizontal, showsIndicators: false, content: {
                HStack{
                    ForEach(0..<colors.count){i in
                        if selectedColor == colors[i]{
                            
                            ZStack(alignment:.center){
                                ItemColorList(color: colors[i],selectedColor: $selectedColor).shadow(radius: 5)
                                Text("")
                                    .frame(width: 32, height: 32, alignment: .center)
                                    .background(Color.white)
                                    .clipShape(Circle())
                                    .shadow(radius: 10)
                                    .padding(.leading,-10)
                            }.onAppear(perform: {
                                isSelectColor = true
                            })
                            
                        }else{
                            ItemColorList(color: colors[i],selectedColor: $selectedColor)
                        }
                        
                    }
                }//HStack
                .padding(.vertical,1)
                .padding(.horizontal,2)
            })//Scroll
           
        }//VStack
    }
}

//struct ColorsListView_Previews: PreviewProvider {
//    static var previews: some View {
//        ColorsListView(colors: ["#ffffff","#000000"],selectedColor: "#ffffff")
//    }
//}
