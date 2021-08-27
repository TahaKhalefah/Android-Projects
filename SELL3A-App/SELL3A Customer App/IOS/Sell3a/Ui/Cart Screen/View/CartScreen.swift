//
//  CartScreen.swift
//  Sell3a
//
//  Created by Hager Samir on 23/06/2021.
//

import SwiftUI

struct CartScreen: View {
    @State private var profileText: String = " "
    @StateObject var viewModel = CartViewModel()
    @State var selection : Int? = nil
    @State var orderrequest : AddOrderRequest?
    @State var totalPrice = 0.0
    @State var itemPrice = 0.0
    @State var count = 0

    
    
    var body: some View {
 
        NavigationView{
            
        VStack{
                List(0..<viewModel.cartItems.count , id : \.self) { i in
                    CartItem(cartItem: viewModel.cartItems[i] ,
                    viewModel: viewModel)
                }.padding()
                .frame( maxHeight: 300)
            
            HStack{
                
                TextField("Enter Cupon Code" , text: $profileText).padding()
                    .foregroundColor(.secondary).border(colorOvelayBlue, width:1).lineLimit(1).frame( height: 50, alignment:.center)
                
                Button(action: {
                   // viewModel.getCart()
                }, label: {
                    Text("Apply").foregroundColor(.white).padding().background(primaryBlue)
                }).padding(.horizontal)
                .onAppear(perform: {
                    //viewModel.getAllItems()
                    totalPrice = 0.0
                    itemPrice = 0.0
                    viewModel.getCartItems()
                    addOrder()
                })
                .onDisappear(perform: {
                    viewModel.cartItems = []
                })

            }
            VStack{
                HStack{
                    Text("Item(\(count))").foregroundColor(.gray)
                    
                    Spacer()
                    
                    Text("\((String(format: "%.2f", itemPrice as! CVarArg)))")
                }.padding()
                
                HStack{
                    Text("Shipping").foregroundColor(.gray)
                    
                    Spacer()
                    
                    Text("13 EGP")
                }.padding()
                
                HStack{
                    Text("Total Price").foregroundColor(.gray)
                    
                    Spacer()
                    
                    Text("\((String(format: "%.2f", totalPrice as! CVarArg))) EGP")
                }.padding()
                
            }.border(colorOvelayBlue, width: 1).padding().cornerRadius(20)
            
        
            NavigationLink(destination: ShipToScreen(order: orderrequest), tag: 2, selection: $selection ){
                Button(action: {
                    selection = 2
                    viewModel.addOrder(request: orderrequest!)
                    viewModel.deleteAllItems()
                }) {
                    Text("Check Out")
                        .bold()
                        .font(.system(size: 14))
                        .foregroundColor(.white)
                        .frame(width: 343, height: 57)
                }
                .background(primaryBlue)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(colorOvelayBlue, lineWidth: 5)
                )
            }
        }
        .navigationBarHidden(true)
        .ignoresSafeArea()
        }
    }
    
     func addOrder(){
        var cartList = viewModel.cartItems
        var itemIds : [ItemIds] = []
        for item in cartList {
            itemPrice = item.price + itemPrice
            var itemData = ItemIds(color: item.selctedColor!, companyName: "Fox for fun", count: Int(item.quantity), id: String(item.id), size: item.selectedSize!)
            itemIds.append(itemData)
        }
        if itemPrice == 0.0 {
            totalPrice = 0.0
        }else{
        totalPrice += itemPrice + 13
        }
        orderrequest?.totalPrice = Int(totalPrice)
        count = cartList.count
        
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        let currentDate = formatter.string(from: date)
        print("Date \(currentDate)")
        
        orderrequest = AddOrderRequest(itemIds: itemIds, orderState: "baking", importCharge: 0, Address: "Cairo", orderCode: generateOrderCode(), orderDate: currentDate, totalPrice: Int(totalPrice), userId: "60cb7238b31e990015aabe72")
    }
    
    func generateOrderCode() -> String{
        let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
         return String((0..<10).map{ _ in letters.randomElement()! })
    }
}


struct CartScreen_Previews: PreviewProvider {
    static var previews: some View {
        CartScreen()
    }
}
